package org.example.lab11.services;

import org.example.lab11.database.Country;
import org.example.lab11.database.InMemoryDatabase;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final InMemoryDatabase database;

    public CountryService(InMemoryDatabase database) {
        this.database = database;
    }

    public List<Country> getAllCountries() {
        return database.getAllCountries();
    }

    public Country addCountry(Country country) {
        return database.addCountry(country);
    }

    public Optional<Country> updateCountry(Long id, String newName) {
        Country updated = database.updateCountry(id, newName);
        return Optional.ofNullable(updated);
    }

    public boolean deleteCountry(Long id) {
        return database.deleteCountry(id);
    }
}
