package org.example.lab11.controllers;

import org.example.lab11.database.City;
import org.example.lab11.services.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping
    public City addCity(@RequestBody City city) {
        return cityService.addCity(city);
    }

    @PutMapping("/{id}")
    public String updateCity(@PathVariable Long id, @RequestParam String name) {
        Optional<City> updatedCity = cityService.updateCity(id, name);
        return updatedCity.isPresent() ? "City updated" : "City not found";
    }

    @DeleteMapping("/{id}")
    public String deleteCity(@PathVariable Long id) {
        boolean deleted = cityService.deleteCity(id);
        return deleted ? "City deleted" : "City not found";
    }
}


