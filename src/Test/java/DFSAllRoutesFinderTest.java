package org.example.ca2.algorithms;

import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DFSAllRoutesFinderTest {

    @Test
    public void findsRouteBetweenConnectedRooms() {
        DFSAllRoutesFinder finder = new DFSAllRoutesFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A", "Room A", 0, 0);
        Room roomB = new Room("B", "Room B", 100, 0);

        graph.addEdge(roomA, roomB, 1.0);

        List<List<Room>> routes = finder.findAllRoutes(graph,roomA,roomB,10);

        assertFalse(routes.isEmpty());
    }

    @Test
    public void returnsEmptyListWhenNoRouteExists() {
        DFSAllRoutesFinder finder = new DFSAllRoutesFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A","Room A",0,0);
        Room roomB = new Room("B","Room B",100,0);

        graph.addRoom(roomA);
        graph.addRoom(roomB);

        List<List<Room>> routes = finder.findAllRoutes(graph,roomA,roomB,10);

        assertTrue(routes.isEmpty());
    }
}