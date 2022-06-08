package ru.artem.perfsystem.db.facade;

import ru.artem.perfsystem.db.dto.*;

public class DbToGrpcFacade {

    public static ru.artem.perfsystem.resource.grpc.Benchmark convertBenchmark(Benchmark benchmark) {
        return ru.artem.perfsystem.resource.grpc.Benchmark.newBuilder()
                .setName(benchmark.getName())
                .build();
    }

    public static ru.artem.perfsystem.resource.grpc.Host convertHost(Host host) {
        return ru.artem.perfsystem.resource.grpc.Host.newBuilder()
                .setName(host.getName())
                .build();
    }

    public static ru.artem.perfsystem.resource.grpc.Jdk convertJdk(Jdk jdk) {
        return ru.artem.perfsystem.resource.grpc.Jdk.newBuilder()
                .setName(jdk.getName())
                .setVersion(jdk.getVersion())
                .build();
    }

    public static ru.artem.perfsystem.resource.grpc.Payload convertPayload(Payload payload) {
        return ru.artem.perfsystem.resource.grpc.Payload.newBuilder()
                .setName(payload.getName())
                .build();
    }

    public static ru.artem.perfsystem.resource.grpc.Report convertReport(Report report) {
        return ru.artem.perfsystem.resource.grpc.Report.newBuilder()
                .setHost(convertHost(report.getHost()))
                .setJdk(convertJdk(report.getJdk()))
                .setPayload(convertPayload(report.getPayload()))
                .setValue(report.getValue())
                .build();
    }

}
