package ru.artem.perfsystem.db.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Optional;

@Entity(name = "jdk")
public class Jdk extends PanacheEntityBase {

    @Id
    @Column(name = "jdk_id", nullable = false)
    @SequenceGenerator(name = "jdkIdSequence", sequenceName = "jdk_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "jdkIdSequence")
    private Integer id;

    @Column(name = "jdk_name", nullable = false)
    private String name;

    @Column(name = "jdk_version", nullable = false)
    private Integer version;

    public static Optional<Jdk> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public static Optional<Jdk> findByNameAndVersion(String name, Integer version) {
        return find("name = ?1 and version = ?2", name, version).firstResultOptional();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Jdk{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }

}
