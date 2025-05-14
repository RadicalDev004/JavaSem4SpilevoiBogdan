package org.example.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class SisterCityId implements java.io.Serializable {
    private static final long serialVersionUID = -6297561324501521670L;
    @Column(name = "city1_id", nullable = false)
    private Integer city1Id;

    @Column(name = "city2_id", nullable = false)
    private Integer city2Id;

    public Integer getCity1Id() {
        return city1Id;
    }

    public void setCity1Id(Integer city1Id) {
        this.city1Id = city1Id;
    }

    public Integer getCity2Id() {
        return city2Id;
    }

    public void setCity2Id(Integer city2Id) {
        this.city2Id = city2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SisterCityId entity = (SisterCityId) o;
        return Objects.equals(this.city1Id, entity.city1Id) &&
                Objects.equals(this.city2Id, entity.city2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city1Id, city2Id);
    }

}