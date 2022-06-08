package ru.artem.perfsystem.util.analytics;

import lombok.ToString;
import ru.artem.perfsystem.db.dto.Benchmark;
import ru.artem.perfsystem.db.dto.Host;
import ru.artem.perfsystem.db.dto.Payload;
import ru.artem.perfsystem.db.dto.Report;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;

import java.util.*;
import java.util.stream.Collectors;

public class Comparison {

    public ComparisonResultWrapper calculateComparison(List<Report> baselineReports, List<Report> comparableReports) {
        validate(baselineReports, comparableReports);

        Set<Entry> baselineEntrySet = new HashSet<>();
        Set<Entry> comparableEntrySet = new HashSet<>();

        for (Report report : baselineReports) {
            Benchmark benchmark = report.getPayload().getBenchmark();
            Host host = report.getHost();
            baselineEntrySet.stream()
                    .filter(entry -> entry.benchmark.equals(benchmark) && entry.host.equals(host))
                    .findAny()
                    .ifPresentOrElse(
                            entry -> entry.reports.add(report),
                            () -> {
                                Entry entry = new Entry();
                                entry.benchmark = benchmark;
                                entry.host = host;
                                entry.reports = new ArrayList<>();
                                entry.reports.add(report);
                                baselineEntrySet.add(entry);
                            });
        }

        for (Report report : comparableReports) {
            Benchmark benchmark = report.getPayload().getBenchmark();
            Host host = report.getHost();
            comparableEntrySet.stream()
                    .filter(entry -> entry.benchmark.equals(benchmark) && entry.host.equals(host))
                    .findAny()
                    .ifPresentOrElse(
                            entry -> entry.reports.add(report),
                            () -> {
                                Entry entry = new Entry();
                                entry.benchmark = benchmark;
                                entry.host = host;
                                entry.reports = new ArrayList<>();
                                entry.reports.add(report);
                                comparableEntrySet.add(entry);
                            });
        }

        baselineEntrySet.removeIf(e -> !comparableEntrySet.contains(e));
        comparableEntrySet.removeIf(e -> !baselineEntrySet.contains(e));

        ComparisonResultWrapper wrapper = new ComparisonResultWrapper();
        for (Entry baselineEntry : baselineEntrySet) {
            for (Entry comparableEntry : comparableEntrySet) {
                if (baselineEntry.equals(comparableEntry)) {
                    ComparisonResultWrapper.BaselineEntry baselineWrapperEntry = new ComparisonResultWrapper.BaselineEntry();
                    baselineWrapperEntry.setBenchmark(baselineEntry.benchmark);
                    baselineWrapperEntry.setHost(baselineEntry.host);
                    baselineWrapperEntry.setReportList(baselineEntry.reports);
                    double baselineGeomean = StatisticUtil.geomean(baselineEntry.reports.stream()
                            .mapToDouble(Report::getValue).toArray());
                    baselineWrapperEntry.setGeomean(baselineGeomean);

                    ComparisonResultWrapper.ComparableEntry comparableWrapperEntry = new ComparisonResultWrapper.ComparableEntry();
                    comparableWrapperEntry.setBenchmark(comparableEntry.benchmark);
                    comparableWrapperEntry.setHost(comparableEntry.host);
                    comparableWrapperEntry.setReportList(comparableEntry.reports);
                    double comparableGeomean = StatisticUtil.geomean(comparableEntry.reports.stream()
                            .mapToDouble(Report::getValue).toArray());
                    comparableWrapperEntry.setGeomean(comparableGeomean);
                    comparableWrapperEntry.setGeomeanRatio(baselineGeomean / comparableGeomean);

                    wrapper.getBaselineEntryList().add(baselineWrapperEntry);
                    wrapper.getComparableEntryList().add(comparableWrapperEntry);
                }
            }
        }
        filterPayloads(wrapper);

        return wrapper;
    }

    private void validate(List<Report> baselineReports, List<Report> comparableReports) {
        if (baselineReports == null || baselineReports.isEmpty()) {
            throw new RuntimeException("No baseline data available");
        }
        if (comparableReports == null || comparableReports.isEmpty()) {
            throw new RuntimeException("No comparable data available");
        }
    }

    private void filterPayloads(ComparisonResultWrapper wrapper) {
        List<ComparisonResultWrapper.BaselineEntry> baselineEntryList = wrapper.getBaselineEntryList();
        List<ComparisonResultWrapper.ComparableEntry> comparableEntryList = wrapper.getComparableEntryList();
        for (ComparisonResultWrapper.BaselineEntry baselineEntry : baselineEntryList) {
            for (ComparisonResultWrapper.ComparableEntry comparableEntry : comparableEntryList) {
                if (baselineEntry.equals(comparableEntry)) {
                    List<Report> baselineReportList = baselineEntry.getReportList();
                    List<Report> comparableReportList = comparableEntry.getReportList();
                    if (baselineReportList.size() != comparableReportList.size()) {
                        List<Payload> baselinePayloadList = baselineReportList.stream()
                                .map(Report::getPayload).collect(Collectors.toList());
                        List<Payload> comparablePayloadList = comparableReportList.stream()
                                .map(Report::getPayload).collect(Collectors.toList());
                        baselineReportList.removeIf(r -> !comparablePayloadList.contains(r.getPayload()));
                        comparableReportList.removeIf(r -> !baselinePayloadList.contains(r.getPayload()));
                    }
                }
            }
        }
    }

    @ToString
    private static class Entry {
        public Benchmark benchmark;
        public Host host;
        public List<Report> reports;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entry)) return false;
            Entry that = (Entry) o;
            return this.benchmark.equals(that.benchmark) && this.host.equals(that.host);
        }

        @Override
        public int hashCode() {
            int result = benchmark != null ? benchmark.hashCode() : 0;
            result = 31 * result + (host != null ? host.hashCode() : 0);
            return result;
        }
    }

}
