package org.example.lab11.database;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryDatabase {

    private final List<City> cityList = new ArrayList<>();
    private final AtomicLong cityIdGenerator = new AtomicLong();

    public List<City> getAllCities() {
        return new ArrayList<>(cityList);
    }

    public City addCity(City city) {
        city.setId(cityIdGenerator.incrementAndGet());
        cityList.add(city);
        return city;
    }

    public City updateCity(Long id, String newName) {
        for (City city : cityList) {
            if (city.getId().equals(id)) {
                city.setName(newName);
                return city;
            }
        }
        return null;
    }

    public boolean deleteCity(Long id) {
        return cityList.removeIf(city -> city.getId().equals(id));
    }

    // Countries
    private final List<Country> countryList = new ArrayList<>();
    private final AtomicLong countryIdGenerator = new AtomicLong();

    public List<Country> getAllCountries() {
        return new ArrayList<>(countryList);
    }

    public Country addCountry(Country country) {
        country.setId(countryIdGenerator.incrementAndGet());
        countryList.add(country);
        return country;
    }

    public Country updateCountry(Long id, String newName) {
        for (Country country : countryList) {
            if (country.getId().equals(id)) {
                country.setName(newName);
                return country;
            }
        }
        return null;
    }

    public boolean deleteCountry(Long id) {
        return countryList.removeIf(country -> country.getId().equals(id));
    }
}

