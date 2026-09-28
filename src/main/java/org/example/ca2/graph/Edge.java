package org.example.ca2.graph;

import org.example.ca2.model.Room;

public class Edge {
    private Room destination;
    private double distance;

    public Edge(Room destination, double distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public Room getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "to " + destination.getId() + " (" + distance + ")";
    }
}
