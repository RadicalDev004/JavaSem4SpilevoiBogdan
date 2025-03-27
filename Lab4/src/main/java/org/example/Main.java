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

        if(args.length != 1) {
            System.out.println("Wrong parameter count.");
        }
        int nrOfLocations = Integer.parseInt(args[0]);

        Faker faker = new Faker();
        Random random = new Random();
        Algo algo = new Algo();

        List<Location> locations = new ArrayList<>();
        Graph<Location, LocationEdge> graph = new SimpleDirectedGraph<>(LocationEdge.class);

        for(int i = 0; i < nrOfLocations; i++)
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
           // System.out.println();
        }
       // System.out.println("All locations " + locations);
        System.out.println("Finished making");
        Function<LocationEdge, Double> weightFunctionTime = edge -> (double) edge.getTime();
        Map<Location, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes = algo.allRoutesFromStart(locations, graph, weightFunctionTime);

        Map<LocationType, List<Location>> locationsByType = locations.stream().collect(Collectors.groupingBy(Location::getLocationType));

        Map<LocationType, Optional<Double>> shortestPathsByType = allRoutes.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getLocationType(),
                        Collectors.mapping(
                                entry -> entry.getValue().getSecond(),
                                Collectors.minBy(Comparator.comparingDouble(Double::doubleValue))
                        )
                ));
        System.out.println("All min to all types " + shortestPathsByType);
        System.out.println();


        Function<LocationEdge, Double> weightFunction2 = edge -> 100 - (double) edge.getProbability();
        Map<Pair<Location, Location>, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes2 = algo.allRoutes(locations, graph, weightFunction2);
        Map<Pair<Location, Location>, Route> typesOfLocationsCount = new HashMap<>();

        for(Pair<Location, Location> pair : allRoutes2.keySet())
        {
            Route route = new Route();
            var path = allRoutes2.get(pair).getFirst();
            for(var node : path.getVertexList())
            {
                route.addLocation(node.getLocationType());
            }
            typesOfLocationsCount.put(pair, route);
            System.out.println(pair + " has " + route);
        }

        Optional<Map.Entry<Pair<Location, Location>, Route>> minFriendlyRoutes = typesOfLocationsCount.entrySet().stream()
                .min(Comparator.comparingInt(entry ->
                        entry.getValue().getFriendlyLocationCount()
                ));
        Optional<Map.Entry<Pair<Location, Location>, Route>> minEnemyRoutes = typesOfLocationsCount.entrySet().stream()
                .min(Comparator.comparingInt(entry ->
                        entry.getValue().getEnemyLocationCount()
                ));
        Optional<Map.Entry<Pair<Location, Location>, Route>> Neutral = typesOfLocationsCount.entrySet().stream()
                .min(Comparator.comparingInt(entry ->
                        entry.getValue().getNeutralLocationCount()
                ));



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