package ru.artem.perfsystem.resource.page.wrapper;

import ru.artem.perfsystem.entity.dto.*;

import java.util.List;

public class OverviewPanelWrapper {

    public List<Benchmark> benchmarkList;
    public List<Host> hostList;
    public List<Jdk> jdkList;
    public List<MetricType> metricTypeList;
    public List<Payload> payloadList;
    public Long reportCount;

}
