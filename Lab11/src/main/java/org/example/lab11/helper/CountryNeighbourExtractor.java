package org.example.lab11.helper;

import com.fasterxml.jackson.databind.*;
import java.util.*;
import java.nio.file.*;

public class CountryNeighbourExtractor {

    public static Map<String, String> read() throws Exception {
        String json = Files.readString(Path.of("C:\\Users\\Bogdan S\\OneDrive\\Documents\\GitHub\\JavaSem4\\Lab11\\src\\main\\java\\org\\example\\lab11\\database\\neighbors.json"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Object>> data = mapper.readValue(json, Map.class);

        Map<String, String> countryMap = new HashMap<>();

        for (Map.Entry<String, Map<String, Object>> entry : data.entrySet()) {
            String country = entry.getKey();
            Object neighboursObj = entry.getValue().get("neighbours");

            if (neighboursObj instanceof List) {
                List<?> neighbours = (List<?>) neighboursObj;
                String joined = String.join(",", (List<String>) neighbours);
                countryMap.put(country, joined);
            }
        }

        /*for (Map.Entry<String, String> entry : countryMap.entrySet()) {
            System.out.println(entry.getKey() + "|" + entry.getValue());
        }*/

        return countryMap;
    }
}
