package ru.artem.perfsystem.util.analytics.wrapper.facade;

import ru.artem.perfsystem.db.facade.DbToGrpcFacade;
import ru.artem.perfsystem.resource.grpc.BaselineEntry;
import ru.artem.perfsystem.resource.grpc.ComparableEntry;
import ru.artem.perfsystem.resource.grpc.ComparisonResponse;
import ru.artem.perfsystem.resource.grpc.Report;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;

import java.util.List;
import java.util.stream.Collectors;

public class WrapperToGrpcFacade {

    public static ru.artem.perfsystem.resource.grpc.ComparisonResponse convertResult(ComparisonResultWrapper wrapper) {
        ComparisonResponse.Builder responseBuilder = ComparisonResponse.newBuilder();

        wrapper.getBaselineEntryList().forEach(baselineEntry -> {
            BaselineEntry.Builder baselineEntryBuilder = BaselineEntry.newBuilder();
            baselineEntryBuilder
                    .setBenchmark(DbToGrpcFacade.convertBenchmark(baselineEntry.getBenchmark()))
                    .setHost(DbToGrpcFacade.convertHost(baselineEntry.getHost()))
                    .setGeomean(baselineEntry.getGeomean());
            List<Report> convertedReportList = baselineEntry.getReportList().stream()
                    .map(DbToGrpcFacade::convertReport).collect(Collectors.toList());
            baselineEntryBuilder.addAllReportList(convertedReportList);
            responseBuilder.addBaselineEntryList(baselineEntryBuilder.build());
        });

        wrapper.getComparableEntryList().forEach(comparableEntry -> {
            ComparableEntry.Builder comparableEntryBuilder = ComparableEntry.newBuilder();
            comparableEntryBuilder
                    .setBenchmark(DbToGrpcFacade.convertBenchmark(comparableEntry.getBenchmark()))
                    .setHost(DbToGrpcFacade.convertHost(comparableEntry.getHost()))
                    .setGeomean(comparableEntry.getGeomean())
                    .setGeomeanRatio(comparableEntry.getGeomeanRatio());
            List<Report> convertedReportList = comparableEntry.getReportList().stream()
                    .map(DbToGrpcFacade::convertReport).collect(Collectors.toList());
            comparableEntryBuilder.addAllReportList(convertedReportList);
            responseBuilder.addComparableEntryList(comparableEntryBuilder.build());
        });

        return responseBuilder.build();
    }

}
