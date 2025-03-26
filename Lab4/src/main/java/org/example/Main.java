package org.example;
import com.github.javafaker.Faker;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.alg.*;
import java.lang.Math;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Location> locations = new ArrayList<>();

        Faker faker = new Faker();
        Random random = new Random();
        Graph<Location, LocationEdge> graph = new SimpleDirectedGraph<>(LocationEdge.class);

        for(int i = 0; i < 10; i++)
        {
            Location newLoc = new Location(faker.name().firstName(), LocationType.fromValue(i % 3));
            locations.add(newLoc);
            graph.addVertex(newLoc);
        }

        for(int i = 0; i < locations.size(); i++)
        {
            for(int j = i + 1; j < locations.size(); j++)
            {
                LocationEdge locEdge = new LocationEdge( Math.abs(random.nextInt() % 5000), Math.abs(random.nextInt()) % 101);
                graph.addEdge(locations.get(i), locations.get(j), locEdge);
                System.out.print(locEdge + " | ");
            }
            System.out.println();
        }

        Function<LocationEdge, Double> weightFunction = edge -> (double) edge.getTime();

        Graph<Location, LocationEdge> weightedGraph = new AsWeightedGraph<>(graph, weightFunction, true, false);

        DijkstraShortestPath<Location, LocationEdge> dijkstra = new DijkstraShortestPath<>(weightedGraph);

        Map<Pair<Location, Location>, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes = new HashMap<>();

        Location start = locations.get(0);
        for(int j = 1; j < locations.size(); j++)
        {
            allRoutes.put(new Pair<>(start, locations.get(j)), new Pair<>(dijkstra.getPath(start, locations.get(j)), dijkstra.getPathWeight(start, locations.get(j))));
        }

        System.out.println("All locations " + locations);

        Map<LocationType, List<Location>> locationsByType = locations.stream().collect(Collectors.groupingBy(Location::getLocationType));

        Map<LocationType, Optional<Double>> shortestPathsByType = allRoutes.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getSecond().getLocationType(),
                        Collectors.mapping(
                                entry -> entry.getValue().getSecond(),
                                Collectors.minBy(Comparator.comparingDouble(Double::doubleValue))
                        )
                ));
        System.out.println("All min to all types " + shortestPathsByType);

        /*Set<Location> friendlyLocations = locations.stream()
                .filter(loc -> loc.getLocationType() == LocationType.friendly)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly Locations (Sorted by Name): " + friendlyLocations);

        List<Location> enemyLocations = locations.stream()
                .filter(loc -> loc.getLocationType() == LocationType.enemy)
                .sorted(Comparator.comparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("Enemy Locations (Sorted by Name): " + enemyLocations);*/
    }
}





//Floyd Warshall, Dijkstra