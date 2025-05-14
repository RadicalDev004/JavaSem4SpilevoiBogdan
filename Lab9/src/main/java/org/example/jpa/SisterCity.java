package org.example.jpa;

import jakarta.persistence.*;

@NamedQuery(
        name = "SisterCity.findByCityId",
        query = "SELECT s FROM SisterCity s WHERE s.city1.id = :cityId OR s.city2.id = :cityId"
)

@Entity
@Table(name = "sister_cities")
public class SisterCity {
    @EmbeddedId
    private SisterCityId id;

    @MapsId("city1Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city1_id", nullable = false)
    private City city1;

    @MapsId("city2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city2_id", nullable = false)
    private City city2;

    public SisterCityId getId() {
        return id;
    }

    public void setId(SisterCityId id) {
        this.id = id;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

}