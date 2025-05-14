package org.example.jpa;

import jakarta.persistence.*;

@NamedQuery(
        name = "Country.findByName",
        query = "SELECT c FROM Country c WHERE LOWER(c.name) LIKE LOWER(:name)"
)

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_id_gen")
    @SequenceGenerator(name = "countries_id_gen", sequenceName = "countries_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Column(name = "continent_id", nullable = false)
    private Integer continentId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getContinentId() {
        return continentId;
    }

    public void setContinentId(Integer continentId) {
        this.continentId = continentId;
    }

}