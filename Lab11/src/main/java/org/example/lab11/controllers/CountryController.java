package org.example.lab11.controllers;

import org.example.lab11.database.Country;
import org.example.lab11.services.CountryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }

    @PutMapping("/{id}")
    public String updateCountry(@PathVariable Long id, @RequestParam String name) {
        Optional<Country> updated = countryService.updateCountry(id, name);
        return updated.isPresent() ? "Country updated" : "Country not found";
    }

    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable Long id) {
        boolean deleted = countryService.deleteCountry(id);
        return deleted ? "Country deleted" : "Country not found";
    }
}
