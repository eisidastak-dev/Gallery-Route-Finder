package org.example.ca2.data;

import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Exhibit;
import org.example.ca2.model.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class GalleryDataLoader {

    public GalleryGraph loadGraphFromCSV(String filename) {
        GalleryGraph graph = new GalleryGraph();

        Map<String, Room> roomMap = new HashMap<>();
        List<String[]> rawRows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            reader.readLine(); // skip header

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);

                if (parts.length < 6) {
                    System.out.println("Skipping bad row: " + line);
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                int x = Integer.parseInt(parts[2].trim());
                int y = Integer.parseInt(parts[3].trim());

                Room room = new Room(id, name, x, y);

                graph.addRoom(room);
                roomMap.put(id, room);

                rawRows.add(parts);
            }

            for (String[] parts : rawRows) {
                String roomId = parts[0].trim();
                Room room = roomMap.get(roomId);

                String connectionData = parts[4].trim();
                String exhibitData = parts[5].trim();

                System.out.println("Loading connections for " + roomId + ": " + connectionData);

                loadConnections(graph, roomMap, room, connectionData);
                loadExhibits(room, exhibitData);
            }

        } catch (Exception e) {
            System.out.println("Error loading gallery CSV file.");
            e.printStackTrace();
        }

        return graph;
    }

    private void loadConnections(
            GalleryGraph graph,
            Map<String, Room> roomMap,
            Room room,
            String connectionData
    ) {
        if (connectionData == null || connectionData.isBlank()) {
            return;
        }

        String[] connectedRoomIds = connectionData.split("\\|");

        for (String connectedId : connectedRoomIds) {
            connectedId = connectedId.trim();

            Room connectedRoom = roomMap.get(connectedId);

            if (connectedRoom != null) {
                graph.addEdge(room, connectedRoom, 1);
                System.out.println("Added edge: " + room.getId() + " -> " + connectedRoom.getId());
            } else {
                System.out.println("Could not find connected room: " + connectedId);
            }
        }
    }

    private void loadExhibits(Room room, String exhibitData) {
        if (exhibitData == null || exhibitData.isBlank()) {
            return;
        }

        String[] exhibits = exhibitData.split("\\|");

        if (exhibits.length >= 2) {
            room.addExhibit(new Exhibit(exhibits[0].trim(), exhibits[1].trim()));
        }
    }
}