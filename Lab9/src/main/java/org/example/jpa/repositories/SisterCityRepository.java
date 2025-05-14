package org.example.jpa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.jpa.Country;
import org.example.jpa.SisterCity;
import org.example.jpa.SisterCityId;

import java.util.List;

public class SisterCityRepository extends AbstractRepository<SisterCity, SisterCityId> {
    public SisterCityRepository(EntityManager em) {
        super(em, SisterCity.class);
    }

    public List<SisterCity> findByName(String namePattern) {
        TypedQuery<SisterCity> query = em.createNamedQuery("Continent.findByName", SisterCity.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}
