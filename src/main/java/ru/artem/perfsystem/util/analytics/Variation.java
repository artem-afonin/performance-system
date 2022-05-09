package ru.artem.perfsystem.util.analytics;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import ru.artem.perfsystem.entity.dto.*;
import ru.artem.perfsystem.resource.page.wrapper.VariationAnalyticsWrapper;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Variation {

    public List<VariationAnalyticsWrapper> calculateVariation() {
        return calculateVariation(null);
    }

    public List<VariationAnalyticsWrapper> calculateVariation(String[] benchmarkNames) {
        List<Report> reportList = findEntities(benchmarkNames);
        reportList = filterEntities(reportList);
        Map<Benchmark, Map<Host, Map<Jdk, Map<Payload, List<Report>>>>> aggregatedReports = aggregateReports(reportList);
        return calculateMetrics(aggregatedReports);
    }

    private List<Report> findEntities(String[] benchmarkNames) {
        StringBuilder query = new StringBuilder();
        query.append("select report\n");
        query.append("from report as report\n");
        query.append("inner join report.payload as payload\n");
        query.append("inner join payload.benchmark as benchmark\n");
        query.append("inner join report.jdk as jdk\n");
        query.append("inner join report.host as host\n");
        if (benchmarkNames != null) {
            if (benchmarkNames.length != 0) {
                query.append("where benchmark.name like '%").append(benchmarkNames[0]).append("%'\n");
            }
            if (benchmarkNames.length > 1) {
                for (int i = 1; i < benchmarkNames.length; i++) {
                    query.append("or benchmark.name like '%").append(benchmarkNames[i]).append("%'\n");
                }
            }
        }
        PanacheQuery<PanacheEntityBase> entities = Report.find(query.toString());
        return entities.stream().map(e -> (Report) e).collect(Collectors.toList());
    }

    private List<Report> filterEntities(List<Report> reportList) {
        reportList = reportList.stream()
                .filter(report -> ! report.getPayload().getName().contains("·p"))
                .collect(Collectors.toList());
        return reportList;
    }

    private Map<Benchmark, Map<Host, Map<Jdk, Map<Payload, List<Report>>>>> aggregateReports(List<Report> reportList) {
        Map<Benchmark, Map<Host, Map<Jdk, Map<Payload, List<Report>>>>> benchMap = new HashMap<>();
        for (Report report : reportList) {
            Benchmark benchmark = report.getPayload().getBenchmark();
            if (!benchMap.containsKey(benchmark)) {
                Map<Host, Map<Jdk, Map<Payload, List<Report>>>> hostMap = new HashMap<>();
                Map<Jdk, Map<Payload, List<Report>>> jdkMap = new HashMap<>();
                Map<Payload, List<Report>> payloadMap = new HashMap<>();
                List<Report> internalReportList = new ArrayList<>();
                internalReportList.add(report);
                payloadMap.put(report.getPayload(), internalReportList);
                jdkMap.put(report.getJdk(), payloadMap);
                hostMap.put(report.getHost(), jdkMap);
                benchMap.put(benchmark, hostMap);
            } else {
                Host host = report.getHost();
                if (!benchMap.get(benchmark).containsKey(host)) {
                    Map<Jdk, Map<Payload, List<Report>>> jdkMap = new HashMap<>();
                    Map<Payload, List<Report>> payloadMap = new HashMap<>();
                    List<Report> internalReportList = new ArrayList<>();
                    internalReportList.add(report);
                    payloadMap.put(report.getPayload(), internalReportList);
                    jdkMap.put(report.getJdk(), payloadMap);
                    benchMap.get(benchmark).put(report.getHost(), jdkMap);
                } else {
                    Jdk jdk = report.getJdk();
                    if (!benchMap.get(benchmark).get(host).containsKey(jdk)) {
                        Map<Payload, List<Report>> payloadMap = new HashMap<>();
                        List<Report> internalReportList = new ArrayList<>();
                        internalReportList.add(report);
                        payloadMap.put(report.getPayload(), internalReportList);
                        benchMap.get(benchmark).get(host).put(report.getJdk(), payloadMap);
                    } else {
                        Payload payload = report.getPayload();
                        if (!benchMap.get(benchmark).get(host).get(jdk).containsKey(payload)) {
                            List<Report> internalReportList = new ArrayList<>();
                            internalReportList.add(report);
                            benchMap.get(benchmark).get(host).get(jdk).put(report.getPayload(), internalReportList);
                        } else {
                            benchMap.get(benchmark).get(host).get(jdk).get(payload).add(report);
                        }
                    }
                }
            }
        }

        return benchMap;
    }

    private List<VariationAnalyticsWrapper> calculateMetrics(Map<Benchmark, Map<Host, Map<Jdk, Map<Payload, List<Report>>>>> aggregatedReports) {
        List<VariationAnalyticsWrapper> wrapperList = new ArrayList<>();
        for (var benchEntry : aggregatedReports.entrySet()) {
            Benchmark benchmark = benchEntry.getKey();
            var hostMap = benchEntry.getValue();
            for (var hostEntry : hostMap.entrySet()) {
                Host host = hostEntry.getKey();
                var jdkMap = hostEntry.getValue();
                for (var jdkEntry : jdkMap.entrySet()) {
                    Jdk jdk = jdkEntry.getKey();
                    var payloadMap = jdkEntry.getValue();
                    for (var payloadEntry : payloadMap.entrySet()) {
                        VariationAnalyticsWrapper newWrapper = new VariationAnalyticsWrapper();
                        Payload payload = payloadEntry.getKey();
                        List<Report> internalReportList = payloadEntry.getValue();
                        newWrapper.benchmark = benchmark;
                        newWrapper.host = host;
                        newWrapper.jdk = jdk;
                        newWrapper.payload = payload;

                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        decimalFormat.setRoundingMode(RoundingMode.CEILING);

                        double[] values = internalReportList.stream().mapToDouble(Report::getValue).toArray();
                        double[] valuesWithoutZeroes = internalReportList.stream().mapToDouble(Report::getValue).filter(v -> v != 0).toArray();
                        if (valuesWithoutZeroes.length > 0) {
                            newWrapper.max = decimalFormat.format(StatisticUtil.max(values));
                            newWrapper.min = decimalFormat.format(StatisticUtil.min(values));
                            newWrapper.mean = decimalFormat.format(StatisticUtil.mean(values));
                            newWrapper.geomean = decimalFormat.format(StatisticUtil.geomean(valuesWithoutZeroes));
                            newWrapper.variation = decimalFormat.format(StatisticUtil.deviation(values)) + "%";
                            newWrapper.stddev = decimalFormat.format(StatisticUtil.stddev(values));
                            newWrapper.p25 = decimalFormat.format(StatisticUtil.percentile(values, 0.25));
                            newWrapper.p50 = decimalFormat.format(StatisticUtil.percentile(values, 0.50));
                            newWrapper.p75 = decimalFormat.format(StatisticUtil.percentile(values, 0.75));
                            newWrapper.p90 = decimalFormat.format(StatisticUtil.percentile(values, 0.90));
                            newWrapper.p99 = decimalFormat.format(StatisticUtil.percentile(values, 0.99));
                            wrapperList.add(newWrapper);
                        }
                    }
                }
            }
        }

        return wrapperList;
    }

}
