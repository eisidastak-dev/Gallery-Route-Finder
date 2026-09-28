package org.example.ca2.app;

import javafx.scene.control.TextField;
import org.example.ca2.algorithms.BFSMapRouteFinder;
import org.example.ca2.model.PixelPoint;
import org.example.ca2.data.GalleryDataLoader;
import org.example.ca2.graph.GalleryGraph;
import org.example.ca2.model.Room;
import org.example.ca2.ui.RouteRenderer;
import org.example.ca2.service.RouteService;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;



import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class MainController {

    private PixelPoint startPoint = null;
    private PixelPoint endPoint = null;

    private GalleryGraph galleryGraph;
    private Room room21;
    private Room room66;

    private final RouteRenderer routeRenderer = new RouteRenderer();

    private final RouteService routeService = new RouteService();

    @FXML
    private ImageView colourMapImageView;

    @FXML
    private Pane graphOverlayPane;

    @FXML
    private ImageView bwMapImageView;

    @FXML
    private Pane bfsOverlayPane;

    @FXML
    private Group colourMapGroup;

    @FXML
    private Group bwMapGroup;

    @FXML
    private ComboBox<Room> startRoomComboBox;

    @FXML
    private ComboBox<Room> endRoomComboBox;



    @FXML
    private ComboBox<Room> avoidRoomComboBox;

    @FXML
    private ComboBox<Room> waypointRoomComboBox;

    @FXML
    private ListView<Room> avoidRoomsListView;

    @FXML
    private ListView<Room> waypointRoomsListView;

    private BufferedImage bufferedMap;
    @FXML
    private TextArea informationField;

    public TextArea informationBFS;

    @FXML
    public void initialize() {
        loadMap();

        loadGraphData();
       // drawDijkstraTestRoute();

        bwMapGroup.setOnMouseClicked(event ->
                handleMapClick(event.getX(), event.getY())
        );
    }

    public void showInformation(String message){
        informationField.setText(message);
        System.out.println(message);
    }

    public void showInformationBFS(String message){
        informationBFS.setText(message);
        System.out.println(message);
    }

    private void loadMap() {
        try {

            Image colourImage = new Image("file:gallery-map.png");
            colourMapImageView.setImage(colourImage);

            Image bwImage = new Image("file:gallery-map-bw.png");
            bwMapImageView.setImage(bwImage);

            bufferedMap = ImageIO.read(new File("gallery-map-bw.png"));

            // Overlay pains must match image pixel size to fix resize bug

            graphOverlayPane.setPrefWidth(colourImage.getWidth());
            graphOverlayPane.setPrefHeight(colourImage.getHeight());

            bfsOverlayPane.setPrefWidth(bwImage.getWidth());
            bfsOverlayPane.setPrefHeight(bwImage.getHeight());

            bfsOverlayPane.setMouseTransparent(true);
            graphOverlayPane.setMouseTransparent(true);

        } catch (Exception e) {
            showInformation("Error loading map image.");
            e.printStackTrace();
        }
    }

    private void handleMapClick(double x, double y) {
        int px = (int) x;
        int py = (int) y;

        if (startPoint == null) {
            startPoint = new PixelPoint(px, py);
            routeRenderer.drawMarker(bfsOverlayPane, px, py, Color.GREEN);
            showInformation("Start set: " + startPoint);
            return;
        }

        if (endPoint == null) {
            endPoint = new PixelPoint(px, py);
            routeRenderer.drawMarker(bfsOverlayPane, px, py, Color.BLUE);
            showInformation("End set: " + endPoint);
            runBFSRoute();
            return;
        }

        startPoint = new PixelPoint(px, py);
        endPoint = null;

        bfsOverlayPane.getChildren().clear();

        routeRenderer.drawMarker(bfsOverlayPane, px, py, Color.GREEN);

        showInformation("Reset. New start set: " + startPoint);
    }

    private void runBFSRoute() {
        if (bufferedMap == null || startPoint == null || endPoint == null) {
            return;
        }

        BFSMapRouteFinder bfs = new BFSMapRouteFinder();

        List<PixelPoint> route = bfs.findRoute(
                bufferedMap,
                startPoint,
                endPoint
        );

        if(route == null || route.isEmpty()){
            showInformationBFS("No BFS route found");
            return;
        }


        routeRenderer.drawPixelRoute(route, bfsOverlayPane, Color.RED);

        showInformationBFS(
                "BFS route found. \n" +
                        "Start pont: " + startPoint + "\n" +
                        "End point: " + endPoint + "\n" +
                        "Route Length: " + route.size() + " pixels"
        );
    }

    private void loadGraphData() {
        GalleryDataLoader loader = new GalleryDataLoader();

        galleryGraph = loader.loadGraphFromCSV("gallery-data.csv");

        galleryGraph.printGraph();

        for (Room room : galleryGraph.getRooms()) {
            if (room.getId().equals("21")) {
                room21 = room;
            }

            if (room.getId().equals("66")) {
                room66 = room;
            }
        }

        startRoomComboBox.getItems().clear();
        endRoomComboBox.getItems().clear();
        avoidRoomComboBox.getItems().clear();
        waypointRoomComboBox.getItems().clear();

        List<Room> sortedRooms = new ArrayList<>(galleryGraph.getRooms());
            //bublle sort
        for(int i = 0; i < sortedRooms.size() - 1; i++){
            for (int j = 0; j< sortedRooms.size()-1 -i;j++) {

                Room room1 = sortedRooms.get(j);
                Room room2 = sortedRooms.get(j+1);

                int room1ID = Integer.parseInt(room1.getId().replace("\"", "").trim());
                int room2ID = Integer.parseInt(room2.getId().replace("\"", "").trim());

                if(room1ID > room2ID){
                    sortedRooms.set(j,room2);
                    sortedRooms.set(j+1 , room1);
                }
            }
        }


        startRoomComboBox.getItems().addAll(sortedRooms);
        endRoomComboBox.getItems().addAll(sortedRooms);
        avoidRoomComboBox.getItems().addAll(sortedRooms);
        waypointRoomComboBox.getItems().addAll(sortedRooms);

        showInformation("Room 21 found: " + (room21 != null));
        showInformation("Room 34 found: " + (room66 != null));
        showInformation("Graph rooms loaded: " + galleryGraph.getRooms().size());
    }



    @FXML
    private void handleDijkstraRoute() {
        if (!startAndEndSelected()) {
            showInformation("Please select a start and destination room.");
            return;
        }

        Room start = getSelectedStartRoom();
        Room end = getSelectedEndRoom();

        Set<Room> avoidRooms = getAvoidRoomsFromUI();
        List<Room> waypoints = getWaypointsFromUI();

        List<Room> route = routeService.findDijkstraRoute(
                galleryGraph,
                start,
                end,
                waypoints,
                avoidRooms
        );


        if (routeIsEmpty(route)) {
            clearGraphRouteAndPrint("No valid Dijkstra route found.");
            return;
        }

        routeRenderer.drawGraphRoute(route, graphOverlayPane, Color.BLUE);

        showInformation(
                "Dijkstra route found. \n" +
                        "Start: " + start + "\n" +
                        "End: " + end + "\n" +
                        "Rooms in route: " + route.size() + "\n" +
                        "Route: " + route
        );
    }

    @FXML
    private void handleDFSRoute() {
        if (!startAndEndSelected()) {
            showInformation("Please select a start and destination room.");
            return;
        }

        Room start = getSelectedStartRoom();
        Room end = getSelectedEndRoom();

        Set<Room> avoidRooms = getAvoidRoomsFromUI();
        List<Room> waypoints = getWaypointsFromUI();

        List<Room> route = routeService.findDFSRoute(
                galleryGraph,
                start,
                end,
                waypoints,
                avoidRooms
        );


        if (routeIsEmpty(route)) {
            clearGraphRouteAndPrint("No valid DFS route found.");
            return;
        }

        routeRenderer.drawGraphRoute(route, graphOverlayPane, Color.ORANGE);

        showInformation(
                "DFS route found. \n" +
                        "Start: " + start + "\n" +
                        "End: " + end + "\n" +
                        "Rooms in route" + route.size() + "\n" +
                        "Route: " + route
        );
    }

    @FXML
    private void handleClearGraphRoute() {
        routeRenderer.clearAll(graphOverlayPane);
        showInformation("Route Clear");
    }

    @FXML
    private void handleClearBFSRoute() {
        routeRenderer.clearAll(bfsOverlayPane);
        startPoint = null;
        endPoint = null;
        showInformation("BFS route clear");
    }

    private Set<Room> getAvoidRoomsFromUI() {
        return new HashSet<>(avoidRoomsListView.getItems());
    }

    private List<Room> getWaypointsFromUI() {
        return new ArrayList<>(waypointRoomsListView.getItems());
    }

    private Room getSelectedStartRoom() {
        return startRoomComboBox.getValue();
    }

    private Room getSelectedEndRoom() {
        return endRoomComboBox.getValue();
    }

    private boolean startAndEndSelected() {
        return getSelectedStartRoom() != null && getSelectedEndRoom() != null;
    }

    private boolean routeIsEmpty(List<Room> route) {
        return route == null || route.isEmpty();
    }

    private void clearGraphRouteAndPrint(String message) {
        routeRenderer.clearAll(graphOverlayPane);
        showInformation(message);
    }

    @FXML
    private void handleAddAvoidRoom() {
        Room selectedRoom = avoidRoomComboBox.getValue();

        if (selectedRoom == null) {
            return;
        }

        if (!avoidRoomsListView.getItems().contains(selectedRoom)) {
            avoidRoomsListView.getItems().add(selectedRoom);
        }
    }

    @FXML
    private void handleClearAvoidRooms() {
        avoidRoomsListView.getItems().clear();
    }

    @FXML
    private void handleAddWaypoint() {
        Room selectedRoom = waypointRoomComboBox.getValue();

        if (selectedRoom == null) {
            return;
        }

        waypointRoomsListView.getItems().add(selectedRoom);
    }


    @FXML
    private void handleClearWaypoints() {
        waypointRoomsListView.getItems().clear();
    }


}