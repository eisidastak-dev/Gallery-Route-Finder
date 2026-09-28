package org.example.ca2.algorithms;

import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.example.ca2.graph.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * This class uses depth-first search (DFS) to find a valid route
 * between two rooms
 */

public class DFSRouteFinder {

    public List<Room> findRoute(GalleryGraph graph, Room start, Room destination) {
        Set<Room> visited = new HashSet<>();
        List<Room> route = new ArrayList<>();

        boolean found = dfs(graph, start, destination, visited, route);

        if (found) {
            return route;
        }

        return new ArrayList<>();
    }

    private boolean dfs(GalleryGraph graph,
                        Room current,
                        Room destination,
                        Set<Room> visited,
                        List<Room> route
    ) {
        visited.add(current);
        route.add(current);

        if(current.equals(destination)) {
            return true;
        }

        for (Edge edge : graph.getConnections(current)) {
            Room nextRoom = edge.getDestination();

            if (!visited.contains(nextRoom)) {
                boolean found = dfs(graph, nextRoom, destination, visited, route);

                if (found) {
                    return true;
                }
            }
        }

        route.remove(route.size() - 1);
        return false;
    }

    public List<Room> findRouteWithWaypoints(
            GalleryGraph graph,
            Room start,
            Room end,
            List<Room> waypoints,
            Set<Room> avoidRooms
    ) {
        if (avoidRooms == null) {
            avoidRooms = new HashSet<>();
        }

        if (waypoints == null) {
            waypoints = new ArrayList<>();
        }

        List<Room> fullRoute = new ArrayList<>();
        Room currentStart = start;

        for (Room waypoint : waypoints) {
            List<Room> segment = findRouteAvoidingRooms(
                    graph,
                    currentStart,
                    waypoint,
                    avoidRooms
            );

            if (segment.isEmpty()) {
                return new ArrayList<>();
            }

            if (!fullRoute.isEmpty()) {
                segment.remove(0);
            }

            fullRoute.addAll(segment);
            currentStart = waypoint;
        }

        List<Room> finalSegment = findRouteAvoidingRooms(
                graph,
                currentStart,
                end,
                avoidRooms
        );

        if (finalSegment.isEmpty()) {
            return new ArrayList<>();
        }

        if (!fullRoute.isEmpty()) {
            finalSegment.remove(0);
        }

        fullRoute.addAll(finalSegment);

        return fullRoute;
    }

    public List<Room> findRouteAvoidingRooms(
            GalleryGraph graph,
            Room start,
            Room end,
            Set<Room> avoidRooms
    ) {
        if (avoidRooms == null) {
            avoidRooms = new HashSet<>();
        }

        if (avoidRooms.contains(start) || avoidRooms.contains(end)) {
            return new ArrayList<>();
        }

        Set<Room> visited = new HashSet<>();
        List<Room> route = new ArrayList<>();

        boolean found = dfsAvoid(
                graph,
                start,
                end,
                avoidRooms,
                visited,
                route
        );

        if (found) {
            return route;
        }

        return new ArrayList<>();
    }

    private boolean dfsAvoid(
            GalleryGraph graph,
            Room current,
            Room end,
            Set<Room> avoidRooms,
            Set<Room> visited,
            List<Room> route
    ) {
        visited.add(current);
        route.add(current);

        if (current.equals(end)) {
            return true;
        }

        for (Edge edge : graph.getConnections(current)) {
            Room next = edge.getDestination();

            if (!visited.contains(next) && !avoidRooms.contains(next)) {
                boolean found = dfsAvoid(
                        graph,
                        next,
                        end,
                        avoidRooms,
                        visited,
                        route
                );

                if (found) {
                    return true;
                }
            }
        }

        route.remove(route.size() - 1);
        return false;
    }
}
