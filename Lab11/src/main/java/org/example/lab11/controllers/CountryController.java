package org.example.lab11.controllers;

import org.example.lab11.database.Country;
import org.example.lab11.helper.CountryNeighbourExtractor;
import org.example.lab11.services.CountryService;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.coloring.GreedyColoring;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/col")
    public List<Country> getCol() {
        List<Country> colored = new ArrayList<>();

        Graph countries = GraphBuilder.empty().buildGraph();
        Map<String, String> countriesWithNeighbours;
        Map<Integer, String> counrtiesWithId = new HashMap<>();
        try {
            countriesWithNeighbours = CountryNeighbourExtractor.read();
            int i = 0;
            for(String country : countriesWithNeighbours.keySet()) {
                countries.addLabeledVertex(country);
                counrtiesWithId.put(i, country);
                i++;
            }
            System.out.println(countries);
            for(var key : countriesWithNeighbours.keySet()) {
                for(var ngh : countriesWithNeighbours.get(key).split(","))
                {
                    if(ngh.isEmpty()) continue;
                    countries.addEdge(key, ngh);
                }
            }
            GreedyColoring gc = new GreedyColoring(countries);

            var cl = gc.findColoring();

            System.out.println("COLORS : " + cl.numUsedColors());

            for(var entry : cl.getColorClasses().entrySet())
            {
                for(var ngh : entry.getValue())
                {
                    Country c = new Country(ngh.longValue(), counrtiesWithId.get(ngh));
                    c.setColor(entry.getKey());
                    colored.add(c);
                }

            }

            System.out.println(countries);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return colored;
    }

}
