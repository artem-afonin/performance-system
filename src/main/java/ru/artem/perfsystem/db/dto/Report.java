package ru.artem.perfsystem.db.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "report")
public class Report extends PanacheEntityBase {

    @Id
    @Column(name = "report_id", nullable = false)
    @SequenceGenerator(name = "reportIdSequence", sequenceName = "report_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "reportIdSequence")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "report_payload_id")
    private Payload payload;

    @ManyToOne
    @JoinColumn(name = "report_metric_type_id")
    private MetricType metricType;

    @ManyToOne
    @JoinColumn(name = "report_jdk_id")
    private Jdk jdk;

    @Column(name = "report_value", nullable = false)
    private Double value;

    @Column(name = "report_date", nullable = false)
    private Timestamp datetime;

    public static List<Report> findByJdk(Jdk jdk) {
        return find("jdk", jdk).list();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public Jdk getJdk() {
        return jdk;
    }

    public void setJdk(Jdk jdk) {
        this.jdk = jdk;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", host=" + host +
                ", payload=" + payload +
                ", metricType=" + metricType +
                ", jdk=" + jdk +
                ", value=" + value +
                ", datetime=" + datetime +
                '}';
    }

}
