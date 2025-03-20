package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Location> locations = Arrays.asList(
                new Location("Alpha", LocationType.friendly),
                new Location("Bravo", LocationType.friendly),
                new Location("Charlie", LocationType.neutral),
                new Location("Delta", LocationType.enemy),
                new Location("Echo", LocationType.enemy),
                new Location("Foxtrot", LocationType.friendly),
                new Location("Gamma", LocationType.enemy)
        );

        Set<Location> friendlyLocations = locations.stream()
                .filter(loc -> loc.getLocationType() == LocationType.friendly)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly Locations (Sorted by Name): " + friendlyLocations);

        List<Location> enemyLocations = locations.stream()
                .filter(loc -> loc.getLocationType() == LocationType.enemy)
                .sorted(Comparator.comparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("Enemy Locations (Sorted by Name): " + enemyLocations);
    }
}





//Floyd Warshall, Dijkstra