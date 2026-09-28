package org.example.ca2.algorithms;

import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraRouteFinderTest {

    @Test
    public void findsShortestRouteBetweenRooms() {
        DijkstraRouteFinder finder=new DijkstraRouteFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A","Room A",0,0);
        Room roomB = new Room("B", "Room B",100,0);

        graph.addEdge(roomA,roomB,5.0);

        List<Room> route = finder.findShortestRoute(graph,roomA,roomB,null);

        assertFalse(route.isEmpty());
        assertEquals(roomA,route.get(0));
        assertEquals(roomB,route.get(route.size() - 1));
    }

    @Test
    public void returnsEmptyListWhenNoRouteExists() {
        DijkstraRouteFinder finder = new DijkstraRouteFinder();
        GalleryGraph graph = new GalleryGraph();

        Room roomA = new Room("A", "Room A", 0, 0);
        Room roomB = new Room("B", "Room B", 100, 0);

        graph.addRoom(roomA);
        graph.addRoom(roomB);

        List<Room> route = finder.findShortestRoute(graph,roomA,roomB,null);

        assertTrue(route.isEmpty());
    }
}