package org.example.jpa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.jpa.City;

import java.util.List;

public class CityRepository extends AbstractRepository<City, Integer> {

    public CityRepository(EntityManager em) {
        super(em, City.class);
    }

    public List<City> findByName(String namePattern) {
        TypedQuery<City> query = em.createNamedQuery("City.findByName", City.class);
        query.setParameter("name", "%" + namePattern + "%");
        return query.getResultList();
    }

    public List<City> findAll() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }
}