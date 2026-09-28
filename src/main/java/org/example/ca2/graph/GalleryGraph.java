package org.example.ca2.graph;

import org.example.ca2.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GalleryGraph {
    private Map<Room, List<Edge>> adjacencyList;

    public GalleryGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addRoom(Room room) {
        adjacencyList.putIfAbsent(room, new ArrayList<>());
    }

    public void addEdge(Room roomA, Room roomB, double disance) {
        addRoom(roomA);
        addRoom(roomB);

        adjacencyList.get(roomA).add(new Edge(roomB, disance));
        adjacencyList.get(roomB).add(new Edge(roomA, disance));
    }

    public List<Edge> getConnections(Room room) {
        return adjacencyList.get(room);
    }

    public Set<Room> getRooms() {
        return adjacencyList.keySet();
    }

    public void printGraph() {
        for (Room room : adjacencyList.keySet()) {
            System.out.println(room + " connects to " + adjacencyList.get(room));
        }
    }
}
