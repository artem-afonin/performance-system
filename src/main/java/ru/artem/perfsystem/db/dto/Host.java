package ru.artem.perfsystem.db.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Optional;

@Entity(name = "host")
public class Host extends PanacheEntityBase {

    @Id
    @Column(name = "host_id", nullable = false)
    @SequenceGenerator(name = "hostIdSequence", sequenceName = "host_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "hostIdSequence")
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
