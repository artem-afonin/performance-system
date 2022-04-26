package ru.artem.perfsystem.entity.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Optional;

@Entity(name = "payload")
public class Payload extends PanacheEntityBase {

    @Id
    @Column(name = "payload_id", nullable = false)
    private Integer id;

    @Column(name = "payload_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "payload_parent_id")
    private Benchmark benchmark;

    public static Optional<Payload> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", benchmark=" + benchmark +
                '}';
    }

}
