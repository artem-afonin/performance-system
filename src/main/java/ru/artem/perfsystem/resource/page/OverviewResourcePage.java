package ru.artem.perfsystem.resource.page;

import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.db.dto.*;
import ru.artem.perfsystem.resource.page.wrapper.OverviewPanelWrapper;
import ru.artem.perfsystem.resource.template.OverviewTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path("/overview")
public class OverviewResourcePage {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getPanel() {
        OverviewPanelWrapper wrapper = new OverviewPanelWrapper();

        wrapper.benchmarkList = Benchmark.listAll().stream().map(b -> (Benchmark) b).collect(Collectors.toUnmodifiableList());
        wrapper.hostList = Host.listAll().stream().map(h -> (Host) h).collect(Collectors.toUnmodifiableList());
        wrapper.jdkList = Jdk.listAll().stream().map(j -> (Jdk) j).collect(Collectors.toUnmodifiableList());
        wrapper.metricTypeList = MetricType.listAll().stream().map(m -> (MetricType) m).collect(Collectors.toUnmodifiableList());
        wrapper.payloadList = Payload.listAll().stream().map(p -> (Payload) p).collect(Collectors.toUnmodifiableList());
        wrapper.reportCount = Report.count();

        return OverviewTemplate.overviewPanel(wrapper);
    }

}
