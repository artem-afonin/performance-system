package ru.artem.perfsystem.resource.grpc;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import ru.artem.perfsystem.db.dto.Report;
import ru.artem.perfsystem.db.service.ReportService;
import ru.artem.perfsystem.util.analytics.Comparison;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;
import ru.artem.perfsystem.util.analytics.wrapper.facade.WrapperToGrpcFacade;

import java.util.List;

@GrpcService
@Blocking
public class AnalyticsService extends AnalyticsGrpc.AnalyticsImplBase {

    @Override
    public void getComparison(ComparisonRequest request, StreamObserver<ComparisonResponse> responseObserver) {
        List<Report> baselineReports = ReportService
                .findLatestByJdkNameAndVersionGroupByHost(request.getBaselineName(), request.getBaselineVersion());
        List<Report> comparableReports = ReportService
                .findLatestByJdkNameAndVersionGroupByHost(request.getComparableName(), request.getComparableVersion());

        Comparison comparisonInstance = new Comparison();
        ComparisonResultWrapper comparisonResult = comparisonInstance.calculateComparison(baselineReports, comparableReports);
        ComparisonResponse response = WrapperToGrpcFacade.convertResult(comparisonResult);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
