package ru.artem.perfsystem.db.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import ru.artem.perfsystem.db.dto.Jdk;
import ru.artem.perfsystem.db.dto.Report;

import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    public static List<Report> findByJdkNameAndVersion(String jdkName, Integer jdkVersion) {
        Jdk jdk = Jdk.findByNameAndVersion(jdkName, jdkVersion).orElseThrow();
        return Report.findByJdk(jdk);
    }

    public static List<Report> findLatestByJdkNameAndVersionGroupByHost(String jdkName, Integer jdkVersion) {
        /*
        select * from report
        where report_id in (
            select max(report.report_id) from report
            inner join payload on report.report_payload_id = payload.payload_id
            inner join benchmark on payload.payload_parent_id = benchmark.benchmark_id
            inner join jdk on report.report_jdk_id = jdk.jdk_id
            inner join host on report.report_host_id = host.host_id
            where jdk.jdk_name = 'zulu'
                and jdk.jdk_version = 11
            group by host.host_name, payload.payload_name
        );
         */
        String query = "select report\n" +
                "from report as report\n" +
                "inner join report.payload as payload\n" +
                "inner join payload.benchmark as benchmark\n" +
                "inner join report.jdk as jdk\n" +
                "inner join report.host as host\n" +
                "where jdk.name = '" + jdkName + "'\n" +
                "and jdk.version = " + jdkVersion + "\n" +
                "group by host.name, payload.name";
        PanacheQuery<PanacheEntityBase> entities = Report.find(query);
        return entities.stream().map(e -> (Report) e).collect(Collectors.toList());
    }

}
