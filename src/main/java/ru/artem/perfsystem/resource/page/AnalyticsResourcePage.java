package ru.artem.perfsystem.resource.page;

import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.resource.page.wrapper.VariationAnalyticsWrapper;
import ru.artem.perfsystem.resource.template.AnalyticsTemplate;
import ru.artem.perfsystem.util.analytics.Variation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/analytics")
public class AnalyticsResourcePage {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getRoot() {
        return AnalyticsTemplate.analyticsPanel();
    }

    @GET
    @Path("/variation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getVariation(@QueryParam("names") String benchmarkNames) {
        String[] benchmarkArray = null;
        if (benchmarkNames != null && !benchmarkNames.equals("")) {
            benchmarkArray = benchmarkNames.split(",");
        }

        Variation variation = new Variation();
        List<VariationAnalyticsWrapper> resultsList = variation.calculateVariation(benchmarkArray);
        return AnalyticsTemplate.variationAnalyticsPanel(resultsList);
    }

}
