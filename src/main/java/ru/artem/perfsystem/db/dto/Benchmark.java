package ru.artem.perfsystem.db.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Optional;


@Entity(name = "benchmark")
public class Benchmark extends PanacheEntityBase {

    @Id
    @Column(name = "benchmark_id", nullable = false)
    @SequenceGenerator(name = "benchmarkIdSequence", sequenceName = "benchmark_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "benchmarkIdSequence")
    private Integer id;

    @Column(name = "benchmark_name", nullable = false)
    private String name;

    public static Optional<Benchmark> findByName(String name) {
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

    @Override
    public String toString() {
        return "Benchmark{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
