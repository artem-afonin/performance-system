package ru.artem.perfsystem.resource.page;

import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.db.dto.Jdk;
import ru.artem.perfsystem.db.dto.Report;
import ru.artem.perfsystem.db.service.ReportService;
import ru.artem.perfsystem.resource.page.wrapper.ComparisonAnalyticsWrapper;
import ru.artem.perfsystem.resource.page.wrapper.VariationAnalyticsWrapper;
import ru.artem.perfsystem.resource.template.AnalyticsTemplate;
import ru.artem.perfsystem.util.analytics.Comparison;
import ru.artem.perfsystem.util.analytics.Variation;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @GET
    @Path("/comparison")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getComparison(@QueryParam("jdks") String jdkString) {
        String[] jdkArray = null;
        if (jdkString != null && !jdkString.equals("")) {
            jdkArray = jdkString.split(",");
        }

        ComparisonAnalyticsWrapper wrapper = new ComparisonAnalyticsWrapper();
        if (jdkArray == null) {
            wrapper.setError("no jdk list provided");
            return AnalyticsTemplate.comparisonAnalyticsPanel(wrapper);
        } else if (jdkArray.length != 2) {
            wrapper.setError("can perform comparison only between two jdks");
            return AnalyticsTemplate.comparisonAnalyticsPanel(wrapper);
        }

        List<Report> baselineReports;
        String[] splittedJdk = jdkArray[0].split("_");
        if (splittedJdk.length != 2) {
            wrapper.setError("incorrect jdk " + jdkArray[0]);
            return AnalyticsTemplate.comparisonAnalyticsPanel(wrapper);
        }
        String jdkName = splittedJdk[0];
        int jdkVersion = Integer.parseInt(splittedJdk[1]);
        baselineReports = ReportService.findLatestByJdkNameAndVersionGroupByHost(jdkName, jdkVersion);

        List<Report> comparableReports;
        splittedJdk = jdkArray[1].split("_");
        if (splittedJdk.length != 2) {
            wrapper.setError("incorrect jdk " + jdkArray[1]);
            return AnalyticsTemplate.comparisonAnalyticsPanel(wrapper);
        }
        jdkName = splittedJdk[0];
        jdkVersion = Integer.parseInt(splittedJdk[1]);
        comparableReports = ReportService.findLatestByJdkNameAndVersionGroupByHost(jdkName, jdkVersion);

        Comparison comparisonInstance = new Comparison();
        ComparisonResultWrapper comparisonResult = comparisonInstance.calculateComparison(baselineReports, comparableReports);
        wrapper.setResult(comparisonResult);

        return AnalyticsTemplate.comparisonAnalyticsPanel(wrapper);
    }

}
