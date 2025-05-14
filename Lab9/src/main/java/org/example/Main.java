package org.example;

import jakarta.persistence.EntityManager;
import org.example.jpa.City;
import org.example.jpa.repositories.AbstractRepository;
import org.example.jpa.repositories.CityRepository;
import org.example.jpa.util.JpaUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        CityRepository repo = new CityRepository(em);

        City city = new City();
        city.setName("Iasi");
        city.setCapital(true);
        city.setLatitude(47.1585);
        city.setLongitude(27.6014);
        city.setCountry("Romania");
        repo.create(city);

        City found = repo.findById(city.getId());
        System.out.println("Found by ID: " + found.getName());

        List<City> results = repo.findByName("ia");
        System.out.println("Matching cities:");
        results.forEach(c -> System.out.println(c.getName()));

        repo.close();
        JpaUtil.shutdown();
    }
}