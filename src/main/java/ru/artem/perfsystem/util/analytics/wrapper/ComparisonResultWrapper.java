package ru.artem.perfsystem.util.analytics.wrapper;

import lombok.*;
import ru.artem.perfsystem.db.dto.Benchmark;
import ru.artem.perfsystem.db.dto.Host;
import ru.artem.perfsystem.db.dto.Jdk;
import ru.artem.perfsystem.db.dto.Report;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComparisonResultWrapper {

    private Jdk baselineJdk;
    private List<BaselineEntry> baselineEntryList = new ArrayList<>();

    private Jdk comparableJdk;
    private List<ComparableEntry> comparableEntryList = new ArrayList<>();

    @RequiredArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BaselineEntry {
        private Benchmark benchmark;
        private Host host;
        private List<Report> reportList;
        private Double geomean;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof BaselineEntry) {
                BaselineEntry that = (BaselineEntry) o;
                return this.benchmark.equals(that.benchmark) && this.host.equals(that.host);
            } else if (o instanceof ComparableEntry) {
                ComparableEntry that = (ComparableEntry) o;
                return this.benchmark.equals(that.benchmark) && this.host.equals(that.host);
            }
            return false;
        }
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ComparableEntry {
        private Benchmark benchmark;
        private Host host;
        private List<Report> reportList;
        private Double geomean;
        private Double geomeanRatio;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof BaselineEntry) {
                BaselineEntry that = (BaselineEntry) o;
                return this.benchmark.equals(that.benchmark) && this.host.equals(that.host);
            } else if (o instanceof ComparableEntry) {
                ComparableEntry that = (ComparableEntry) o;
                return this.benchmark.equals(that.benchmark) && this.host.equals(that.host);
            }
            return false;
        }
    }

}
