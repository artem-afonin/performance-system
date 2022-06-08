package ru.artem.perfsystem.resource.json;

import lombok.Data;
import lombok.NonNull;
import ru.artem.perfsystem.db.dto.Report;
import ru.artem.perfsystem.db.service.ReportService;
import ru.artem.perfsystem.resource.page.wrapper.ComparisonAnalyticsWrapper;
import ru.artem.perfsystem.util.analytics.Comparison;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/analytics")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnalyticsResource {

    @GET
    @Path("/comparison")
    public Response getComparison(ComparisonRequest request) {
        List<Report> baselineReports = ReportService
                .findLatestByJdkNameAndVersionGroupByHost(request.baselineName, request.baselineVersion);
        List<Report> comparableReports = ReportService
                .findLatestByJdkNameAndVersionGroupByHost(request.comparableName, request.comparableVersion);

        ComparisonAnalyticsWrapper wrapper = new ComparisonAnalyticsWrapper();
        Comparison comparisonInstance = new Comparison();
        ComparisonResultWrapper comparisonResult = comparisonInstance.calculateComparison(baselineReports, comparableReports);
        wrapper.setResult(comparisonResult);

        return Response.ok(wrapper).build();
    }

    @Data
    private static class ComparisonRequest {
        @NonNull
        private String baselineName;
        @NonNull
        private Integer baselineVersion;
        @NonNull
        private String comparableName;
        @NonNull
        private Integer comparableVersion;
    }

}
