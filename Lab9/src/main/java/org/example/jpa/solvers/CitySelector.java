package org.example.jpa.solvers;

import jakarta.persistence.EntityManager;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import org.example.jpa.City;
import org.example.jpa.repositories.CityRepository;
import org.example.jpa.util.JpaUtil;

import java.util.*;

public class CitySelector {

    public static void main(String[] args) {
        char startingLetter = 'C';
        int minPopulation = 1_000_000;
        int maxPopulation = 5_000_000;

        List<City> allCities = loadFromDatabase();
        List<City> eligibleCities = new ArrayList<>();
        Set<String> usedCountries = new HashSet<>();

        for (City city : allCities) {
            if (city.getName().toUpperCase().startsWith(String.valueOf(startingLetter).toUpperCase())
                    && usedCountries.add(city.getCountry())) {
                eligibleCities.add(city);
            }
        }

        int n = eligibleCities.size();
        if (n == 0) {
            System.out.println("No eligible cities found.");
            return;
        }

        Model model = new Model("City Selector");
        IntVar[] selected = model.boolVarArray("selected", n);

        int[] populations = eligibleCities.stream()
                .mapToInt(c -> Optional.ofNullable(c.getPopulation()).orElse(0))
                .toArray();

        model.scalar(selected, populations, ">=", minPopulation).post();
        model.scalar(selected, populations, "<=", maxPopulation).post();

        Solver solver = model.getSolver();
        if (solver.solve()) {
            System.out.println("Selected cities:");
            for (int i = 0; i < n; i++) {
                if (selected[i].getValue() == 1) {
                    City city = eligibleCities.get(i);
                    System.out.printf("- %s (%s), Population: %d%n",
                            city.getName(), city.getCountry(), city.getPopulation());
                }
            }
        } else {
            System.out.println("No valid combination found.");
        }
    }
    public static List<City> loadFromDatabase() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        CityRepository cityRepo = new CityRepository(em);
        List<City> cities = cityRepo.findAll();
        cityRepo.close();
        return cities;
    }
}
