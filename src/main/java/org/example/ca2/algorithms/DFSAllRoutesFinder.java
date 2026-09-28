package org.example.ca2.algorithms;

import org.example.ca2.graph.Edge;
import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;

import java.util.*;

/**
 * This class finds multiple routes between two rooms using
 * depth first search (DFS).
 */

public class DFSAllRoutesFinder {

    public List<List<Room>> findAllRoutes(
            GalleryGraph graph,
            Room start,
            Room destination,
            int maxRoutes
    ) {
        List<List<Room>> allRoutes = new ArrayList<>();
        List<Room> currentPath = new ArrayList<>();
        Set<Room> visited = new HashSet<>();

        dfs(graph, start, destination, visited, currentPath, allRoutes, maxRoutes);

        return allRoutes;
    }

    private void dfs(
            GalleryGraph graph,
            Room current,
            Room destination,
            Set<Room> visited,
            List<Room> currentPath,
            List<List<Room>> allRoutes,
            int maxRoutes
    ) {
        // stop if a limit is reached

        if (allRoutes.size() == maxRoutes) {
            return;
        }

        visited.add(current);
        currentPath.add(current);

        if (current.equals(destination)) {
            // found a full path and store a copy
            allRoutes.add(new ArrayList<>(currentPath));
        } else {
            for (Edge edge : graph.getConnections(current)) {
                Room next = edge.getDestination();

                if (!visited.contains(next)) {
                    dfs(graph, next, destination, visited, currentPath, allRoutes, maxRoutes);
                }
            }
        }

        // now backtrack!

        visited.remove(current);
        currentPath.remove(currentPath.size() -1);
    }


}
