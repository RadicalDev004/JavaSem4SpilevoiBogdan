package org.example.lab11.services;

import org.example.lab11.database.City;
import org.example.lab11.database.InMemoryDatabase;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final InMemoryDatabase database;

    public CityService(InMemoryDatabase database) {
        this.database = database;
    }

    public List<City> getAllCities() {
        return database.getAllCities();
    }

    public City addCity(City city) {
        return database.addCity(city);
    }

    public Optional<City> updateCity(Long id, String newName) {
        City updatedCity = database.updateCity(id, newName);
        return Optional.ofNullable(updatedCity);
    }

    public boolean deleteCity(Long id) {
        return database.deleteCity(id);
    }
}