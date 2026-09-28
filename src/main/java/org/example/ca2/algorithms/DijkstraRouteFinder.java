package org.example.ca2.algorithms;

import org.example.ca2.graph.Edge;
import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.example.ca2.model.Exhibit;

import java.util.*;

public class DijkstraRouteFinder {

    public List<Room> findShortestRoute(GalleryGraph graph, Room start, Room destination, Set<Room> avoidRooms) {

        if (avoidRooms == null) {
            avoidRooms = new HashSet<>();
        }

        if (avoidRooms.contains(start) || avoidRooms.contains(destination)) {
            return new ArrayList<>();
        }

        Map<Room, Double> distances = new HashMap<>();
        Map<Room, Room> previousRooms = new HashMap<>();
        PriorityQueue<RoomDistance> queue = new PriorityQueue<>();

        for (Room room : graph.getRooms()) {
            distances.put(room, Double.MAX_VALUE);
        }

        distances.put(start, 0.0);
        queue.add(new RoomDistance(start,0.0));

        while (!queue.isEmpty()) {
            RoomDistance currentRoomDistance = queue.poll();
            Room currentRoom = currentRoomDistance.getRoom();

            if (currentRoom.equals(destination)) {
                break;
            }

            for (Edge edge : graph.getConnections(currentRoom)) {
                Room neighbour = edge.getDestination();

                if (avoidRooms.contains(neighbour)) {
                    continue;
                }

                double newDistance = distances.get(currentRoom) + edge.getDistance();

                if (newDistance < distances.get(neighbour)) {
                    distances.put(neighbour, newDistance);
                    previousRooms.put(neighbour, currentRoom);
                    queue.add(new RoomDistance(neighbour, newDistance));
                }

            }
        }

        return buildRoute(previousRooms, start, destination);
    }

    private List<Room> buildRoute(Map<Room, Room> previousRooms, Room start, Room destination) {
        List<Room> route = new ArrayList<>();
        Room current = destination;

        while(current != null) {
            route.add(current);
            current = previousRooms.get(current);
        }

        Collections.reverse(route);

        if (!route.isEmpty() && route.get(0).equals(start)) {
            return route;
        }

        return new ArrayList<>();
    }

    public List<Room> findRouteWithWaypoints(
            GalleryGraph graph,
            Room start,
            Room destination,
            List<Room> waypoints,
            Set<Room> avoidRooms
    ) {
        List<Room> fullRoute = new ArrayList<>();

        Room currentStart = start;

        // go through each waypoint

        for (Room waypoint : waypoints) {
            List<Room> segment = findShortestRoute(graph, currentStart, waypoint, avoidRooms);

            if (segment.isEmpty()) {
                return new ArrayList<>(); // no route is possible!
            }

            //avoid duplicating nodes
            if (!fullRoute.isEmpty()) {
                segment.remove(0);
            }

            fullRoute.addAll(segment);
            currentStart = waypoint;
        }

        //final leg: last waypoint -> destination

        List<Room> finalSegment = findShortestRoute(graph, currentStart, destination, avoidRooms);

        if (finalSegment.isEmpty()) {
            return new ArrayList<>();
        }

        if (!fullRoute.isEmpty()) {
            finalSegment.remove(0);
        }

        fullRoute.addAll(finalSegment);

        return fullRoute;
    }

    public List<Room> findMostInterestingRoute(
            GalleryGraph graph,
            Room start,
            Room destination,
            Set<Room> avoidRooms,
            Set<String> preferredArtists
    ) {
        if (avoidRooms == null) {
            avoidRooms = new HashSet<>();
        }

        if (preferredArtists == null) {
            preferredArtists = new HashSet<>();
        }

        if (avoidRooms.contains(start) || avoidRooms.contains(destination)) {
            return new ArrayList<>();
        }

        Map<Room, Double> scores = new HashMap<>();
        Map<Room, Room> previousRooms = new HashMap<>();
        PriorityQueue<RoomDistance> queue = new PriorityQueue<>();

        for (Room room : graph.getRooms()) {
            scores.put(room, Double.MAX_VALUE);
        }

        scores.put(start, 0.0);
        queue.add(new RoomDistance(start, 0.0));

        while (!queue.isEmpty()) {
            RoomDistance currentRoomDistance = queue.poll();
            Room currentRoom = currentRoomDistance.getRoom();

            if (currentRoom.equals(destination)) {
                break;
            }

            for (Edge edge : graph.getConnections(currentRoom)) {
                Room neighbour = edge.getDestination();

                if (avoidRooms.contains(neighbour)) {
                    continue;
                }

                double cost = edge.getDistance();

                if (containsPrefferedArtist(neighbour, preferredArtists)) {
                    cost -= 5.0;
                }

                if (cost < 1.0) {
                    cost = 1.0;
                }

                double newScore = scores.get(currentRoom) + cost;

                if (newScore < scores.get(neighbour)) {
                    scores.put(neighbour, newScore);
                    previousRooms.put(neighbour, currentRoom);
                    queue.add(new RoomDistance(neighbour, newScore));
                }
            }


        }

        return buildRoute(previousRooms,start,destination);
    }

    private boolean containsPrefferedArtist(Room room, Set<String> prefferedArtists) {
        for (Exhibit exhibit : room.getExhibits()) {
            if(prefferedArtists.contains(exhibit.getArtist())) {
                return true;
            }
        }
        return false;
    }

    private static class RoomDistance implements Comparable<RoomDistance> {
        private Room room;
        private double distance;

        public RoomDistance(Room room, double distance) {
            this.room = room;
            this.distance = distance;
        }

        public Room getRoom() {
            return room;
        }

        @Override
        public int compareTo(RoomDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

}
