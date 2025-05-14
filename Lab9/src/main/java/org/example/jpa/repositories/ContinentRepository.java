package org.example.jpa.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.jpa.Continent;

import java.util.List;

public class ContinentRepository extends AbstractRepository<Continent, Integer> {
    public ContinentRepository(EntityManager em) {
        super(em, Continent.class);
    }

    public List<Continent> findByName(String namePattern) {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findByName", Continent.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }
}
