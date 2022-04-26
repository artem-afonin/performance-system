package ru.artem.perfsystem.entity.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Optional;

@Entity(name = "host")
public class Host extends PanacheEntityBase {

    @Id
    @Column(name = "host_id", nullable = false)
    private Integer id;

    @Column(name = "host_name", nullable = false)
    private String name;

    public static Optional<Host> findByName(String name) {
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
        return "Host{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
