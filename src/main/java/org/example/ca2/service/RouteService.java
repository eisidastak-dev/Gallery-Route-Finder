package org.example.ca2.service;

import org.example.ca2.algorithms.DFSRouteFinder;
import org.example.ca2.algorithms.DijkstraRouteFinder;
import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;

import java.util.List;
import java.util.Set;

public class RouteService {

    private final DijkstraRouteFinder dijkstraRouteFinder;
    private final DFSRouteFinder dfsRouteFinder;

    public RouteService() {
        dijkstraRouteFinder = new DijkstraRouteFinder();
        dfsRouteFinder = new DFSRouteFinder();
    }

    public List<Room> findDijkstraRoute(
            GalleryGraph graph,
            Room start,
            Room end,
            List<Room> waypoints,
            Set<Room> avoidRooms
    ) {
        return dijkstraRouteFinder.findRouteWithWaypoints(
                graph,
                start,
                end,
                waypoints,
                avoidRooms
        );
    }

    public List<Room> findDFSRoute(
            GalleryGraph graph,
            Room start,
            Room end,
            List<Room> waypoints,
            Set<Room> avoidRooms
    ) {
        return dfsRouteFinder.findRouteWithWaypoints(
                graph,
                start,
                end,
                waypoints,
                avoidRooms
        );
    }
}