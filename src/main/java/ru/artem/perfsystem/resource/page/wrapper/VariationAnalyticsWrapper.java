package ru.artem.perfsystem.resource.page.wrapper;

import ru.artem.perfsystem.entity.dto.Benchmark;
import ru.artem.perfsystem.entity.dto.Host;
import ru.artem.perfsystem.entity.dto.Jdk;
import ru.artem.perfsystem.entity.dto.Payload;

public class VariationAnalyticsWrapper {

    public Benchmark benchmark;
    public Host host;
    public Jdk jdk;
    public Payload payload;
    public String max;
    public String min;
    public String mean;
    public String geomean;
    public String variation;
    public String stddev;

    public String p25;
    public String p50;
    public String p75;
    public String p90;
    public String p99;

}
