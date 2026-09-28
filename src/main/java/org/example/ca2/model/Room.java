package org.example.ca2.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private String name;
    private int x;
    private int y;

    private List<Exhibit> exhibits;

    public Room(String id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.exhibits = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void addExhibit(Exhibit exhibit) {
        exhibits.add(exhibit);
    }

    @Override
    public String toString() {
        return name;
    }
}
