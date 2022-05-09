package ru.artem.perfsystem.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import ru.artem.perfsystem.entity.dto.*;
import ru.artem.perfsystem.exception.LogParsingException;
import ru.artem.perfsystem.util.csv.LogCsvBean;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser {

    private static final Pattern scoreLinePattern = Pattern.compile("^([^ ]+).*?(\\d+\\.\\d+) ±.*?(\\d+\\.\\d+).*?([^ ]+)$");

//    public List<Report> parse(
//            List<String> logContent,
//            String benchmarkName,
//            String hostName,
//            String jdkName,
//            Integer jdkVersion) throws LogParsingException {
//        logContent = List.copyOf(logContent);
//
//        int resultStartIndex = -1;
//        for (int i = 0; i < logContent.size(); i++) {
//            if (logContent.get(i).matches("^# Run complete\\. Total time:.*$")) {
//                resultStartIndex = i;
//                break;
//            }
//        }
//        if (resultStartIndex == -1) throw new LogParsingException("Can`t find results section");
//
//        int scoreStartIndex = -1;
//        for (int i = resultStartIndex; i < logContent.size(); i++) {
//            if (logContent.get(i).matches("^Benchmark.*Mode.*Score.*Error.*Units$")) {
//                scoreStartIndex = i + 1;
//                break;
//            }
//        }
//        if (scoreStartIndex == -1) throw new LogParsingException("Can`t find score section");
//
//        Benchmark benchmark = Benchmark.findByName(benchmarkName).orElseGet(() -> {
//            Benchmark newBenchmark = new Benchmark();
//            newBenchmark.setName(benchmarkName);
//            return newBenchmark;
//        });
//        benchmark.persist();
//        Host host = Host.findByName(hostName).orElseGet(() -> {
//            Host newHost = new Host();
//            newHost.setName(hostName);
//            return newHost;
//        });
//        host.persist();
//        Jdk jdk = Jdk.findByNameAndVersion(jdkName, jdkVersion).orElseGet(() -> {
//            Jdk newJdk = new Jdk();
//            newJdk.setName(jdkName);
//            newJdk.setVersion(jdkVersion);
//            return newJdk;
//        });
//        jdk.persist();
//
//        List<Report> reportList = new ArrayList<>(logContent.size() - scoreStartIndex);
//        Timestamp currentTs = new Timestamp(System.currentTimeMillis());
//        for (int i = scoreStartIndex; i < logContent.size(); i++) {
//            if (logContent.get(i).isBlank()) continue;
//            if (logContent.get(i).matches(".*≈ 10⁻.*")) continue;
//            Matcher matcher = scoreLinePattern.matcher(logContent.get(i));
//            if (!matcher.matches()) throw new LogParsingException("Score line do not matches pattern");
//            Report newReport = new Report();
//
//            Payload payload = Payload.findByBenchmarkAndName(benchmark, matcher.group(1)).orElseGet(() -> {
//                Payload newPayload = new Payload();
//                newPayload.setBenchmark(benchmark);
//                newPayload.setName(matcher.group(1));
//                return newPayload;
//            });
//            payload.persist();
//            MetricType metricType = MetricType.findByName(matcher.group(4)).orElseGet(() -> {
//                MetricType newMetricType = new MetricType();
//                newMetricType.setName(matcher.group(4));
//                return newMetricType;
//            });
//            metricType.persist();
//
//            newReport.setHost(host);
//            newReport.setPayload(payload);
//            newReport.setMetricType(metricType);
//            newReport.setJdk(jdk);
//            newReport.setValue(Double.valueOf(matcher.group(2)));
//            newReport.setDatetime(currentTs);
//            reportList.add(newReport);
//        }
//
//        return reportList;
//    }

    public List<Report> parse(List<String> logContent, String benchmarkName, String hostName, String jdkName, Integer jdkVersion) throws LogParsingException, CsvException, IOException {

//        CSVReader reader = new CSVReader(new StringReader(String.join("", logContent)));
//        String[] header = reader.readNext();
//        List<String[]> content = reader.readAll();

        List<LogCsvBean> rows = new CsvToBeanBuilder(new StringReader(String.join("", logContent)))
                .withSkipLines(1)
                .withQuoteChar('\"')
                .withType(LogCsvBean.class)
                .build()
                .parse();

        Benchmark benchmark = Benchmark.findByName(benchmarkName).orElseGet(() -> {
            Benchmark newBenchmark = new Benchmark();
            newBenchmark.setName(benchmarkName);
            return newBenchmark;
        });
        benchmark.persist();
        Host host = Host.findByName(hostName).orElseGet(() -> {
            Host newHost = new Host();
            newHost.setName(hostName);
            return newHost;
        });
        host.persist();
        Jdk jdk = Jdk.findByNameAndVersion(jdkName, jdkVersion).orElseGet(() -> {
            Jdk newJdk = new Jdk();
            newJdk.setName(jdkName);
            newJdk.setVersion(jdkVersion);
            return newJdk;
        });
        jdk.persist();

        List<Report> reportList = new ArrayList<>(rows.size());
        Timestamp currentTs = new Timestamp(System.currentTimeMillis());

        rows.forEach(row -> {
            Report newReport = new Report();

            String metricTypeName = row.getUnits();
            MetricType metricType = MetricType.findByName(metricTypeName).orElseGet(() -> {
                MetricType newMetricType = new MetricType();
                newMetricType.setName(metricTypeName);
                return newMetricType;
            });
            metricType.persist();

            String payloadName;
            if (row.getParameters() != null)
                payloadName = row.getPayload() + "(" + String.join(",", row.getParameters().values()) + ")";
            else
                payloadName = row.getPayload() + "()";
            Payload payload = Payload.findByBenchmarkAndName(benchmark, payloadName).orElseGet(() -> {
                Payload newPayload = new Payload();
                newPayload.setBenchmark(benchmark);
                newPayload.setName(payloadName);
                return newPayload;
            });
            payload.persist();

            newReport.setHost(host);
            newReport.setPayload(payload);
            newReport.setMetricType(metricType);
            newReport.setJdk(jdk);
            newReport.setValue(row.getScore());
            newReport.setDatetime(currentTs);
            reportList.add(newReport);
        });

        return reportList;
    }

}
