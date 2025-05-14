package org.example.jpa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.jpa.Continent;
import org.example.jpa.Country;

import java.util.List;

public class CountryRepository extends AbstractRepository<Country, Integer> {
    public CountryRepository(EntityManager em) {
        super(em, Country.class);
    }

    public List<Country> findByName(String namePattern) {
        TypedQuery<Country> query = em.createNamedQuery("Continent.findByName", Country.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}
