package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsWeightedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Algo {

    public Map<Location, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutesFromStart(List<Location> locations, Graph<Location, LocationEdge> graph, Function<LocationEdge, Double> weightFunction)
    {
        Map<Location, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes = new HashMap<>();

        Graph<Location, LocationEdge> weightedGraph = new AsWeightedGraph<>(graph, weightFunction, true, false);
        DijkstraShortestPath<Location, LocationEdge> dijkstra = new DijkstraShortestPath<>(weightedGraph);

        Location start = locations.get(0);
        for(int j = 1; j < locations.size(); j++)
        {
            var otherNode = locations.get(j);
            var path = dijkstra.getPath(start, otherNode);
            var pathWeight = dijkstra.getPathWeight(start, otherNode);

            allRoutes.put(otherNode, new Pair<>(path, pathWeight));
        }

        return allRoutes;
    }
    public Map<Pair<Location, Location>, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes(List<Location> locations, Graph<Location, LocationEdge> graph, Function<LocationEdge, Double> weightFunction)
    {
        Map<Pair<Location, Location>, Pair<GraphPath<Location, LocationEdge>, Double>> allRoutes = new HashMap<>();

        Graph<Location, LocationEdge> weightedGraph = new AsWeightedGraph<>(graph, weightFunction, true, false);
        DijkstraShortestPath<Location, LocationEdge> dijkstra = new DijkstraShortestPath<>(weightedGraph);

        for(int i = 0; i < locations.size(); i++)
        {
            var start = locations.get(i);
            for(int j = i + 1; j < locations.size(); j++)
            {
                var otherNode = locations.get(j);
                var path = dijkstra.getPath(start, otherNode);
                var pathWeight = dijkstra.getPathWeight(start, otherNode);

                allRoutes.put(new Pair<>(start, otherNode), new Pair<>(path, pathWeight));
            }
        }

        return allRoutes;
    }
}
