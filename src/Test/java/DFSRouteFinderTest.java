package org.example.ca2.algorithms;

import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DFSRouteFinderTest {

    @Test
    public void findsRouteBetweenConnectedRooms() {
        DFSRouteFinder finder = new DFSRouteFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A","Room A",0,0);
        Room roomB = new Room("B","Room B",100,0);

        graph.addEdge(roomA,roomB,1.0);

        List<Room> route = finder.findRoute(graph,roomA,roomB);

        assertFalse(route.isEmpty());
    }

    @Test
    public void returnsEmptyListWhenNoRouteExists() {
        DFSRouteFinder finder = new DFSRouteFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A","Room A",0,0);
        Room roomB = new Room("B","Room B",100,0);

        graph.addRoom(roomA);
        graph.addRoom(roomB);

        List<Room> route = finder.findRoute(graph,roomA,roomB);

        assertTrue(route.isEmpty());
    }
}