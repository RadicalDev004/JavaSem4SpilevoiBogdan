package org.example.jpa;

import jakarta.persistence.*;

@NamedQuery(
        name = "Continent.findByName",
        query = "SELECT c FROM Continent c WHERE LOWER(c.name) LIKE LOWER(:name)"
)


@Entity
@Table(name = "continents")
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continents_id_gen")
    @SequenceGenerator(name = "continents_id_gen", sequenceName = "continents_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

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

}