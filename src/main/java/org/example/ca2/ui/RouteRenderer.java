package org.example.ca2.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import org.example.ca2.model.PixelPoint;
import org.example.ca2.model.Room;

import java.util.List;

public class RouteRenderer {

    public void drawGraphRoute(List<Room> route, Pane overlayPane, Color colour) {
        clearRoutes(overlayPane);

        if (route == null || route.isEmpty()) {
            System.out.println("No graph route found.");
            return;
        }

        Polyline line = createGraphPolyline(route, colour);
        overlayPane.getChildren().add(line);
    }

    public void drawPixelRoute(List<PixelPoint> route, Pane overlayPane, Color colour) {
        clearRoutes(overlayPane);

        if (route == null || route.isEmpty()) {
            System.out.println("No pixel route found.");
            return;
        }

        Polyline line = createPixelPolyline(route, colour);
        overlayPane.getChildren().add(line);
    }

    public void drawMarker(Pane overlayPane, int x, int y, Color colour) {
        Circle marker = new Circle(x, y, 6);
        marker.setFill(colour);

        overlayPane.getChildren().add(marker);
    }

    public void clearRoutes(Pane overlayPane) {
        overlayPane.getChildren().removeIf(node -> node instanceof Polyline);
    }

    public void clearAll(Pane overlayPane) {
        overlayPane.getChildren().clear();
    }

    private Polyline createGraphPolyline(List<Room> route, Color colour) {
        Polyline line = new Polyline();

        for (Room room : route) {
            line.getPoints().add((double) room.getX());
            line.getPoints().add((double) room.getY());
        }

        line.setStroke(colour);
        line.setStrokeWidth(4);

        return line;
    }

    private Polyline createPixelPolyline(List<PixelPoint> route, Color colour) {
        Polyline line = new Polyline();

        for (PixelPoint point : route) {
            line.getPoints().add((double) point.getX());
            line.getPoints().add((double) point.getY());
        }

        line.setStroke(colour);
        line.setStrokeWidth(3);

        return line;
    }
}