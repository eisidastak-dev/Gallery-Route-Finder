//package org.example.ca2.app;
//
//import org.example.ca2.algorithms.DFSRouteFinder;
//import org.example.ca2.algorithms.DFSAllRoutesFinder;
//import org.example.ca2.algorithms.DijkstraRouteFinder;
//import org.example.ca2.graph.GalleryGraph;
//import org.example.ca2.model.Exhibit;
//import org.example.ca2.model.Room;
//import org.example.ca2.algorithms.BFSMapRouteFinder;
//import org.example.ca2.model.PixelPoint;
//import org.example.ca2.util.ImageRouteDrawer;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//import java.util.HashSet;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//
//        GalleryGraph graph = new GalleryGraph();
//
//        Room roomA = new Room("A", "Entrance Hall");
//        Room roomB = new Room("B", "Portrait Room");
//        Room roomC = new Room("C", "Landscape Room");
//        Room roomD = new Room("D", "Renaissance Room");
//
//        roomA.addExhibit(new Exhibit("Sunflowers", "Vincent van Gogh"));
//        roomB.addExhibit(new Exhibit("The Fighting Temeraire", "J. M. W. Turner"));
//        roomC.addExhibit(new Exhibit("Water Lilies", "Claude Monet"));
//
//        graph.addEdge(roomA, roomB,5);
//        graph.addEdge(roomA, roomC,8);
//        graph.addEdge(roomB, roomD,4);
//        graph.addEdge(roomC, roomD,2);
//
//        graph.printGraph();
//
//        DFSRouteFinder dfs = new DFSRouteFinder();
//
//        List<Room> route = dfs.findRoute(graph, roomA, roomD);
//
//        DFSAllRoutesFinder allFinder = new DFSAllRoutesFinder();
//
//        List<List<Room>> routes = allFinder.findAllRoutes(graph, roomA, roomD, 10);
//
//        System.out.println("\nAll routes from A to D:");
//
//        for (List<Room> r : routes) {
//            System.out.println(r);
//        }
//
////        System.out.println("\nDFS route from A to D:");
////        for (Room room : route) {
////            System.out.println(room);
////        }
//
//        DijkstraRouteFinder dijkstra = new DijkstraRouteFinder();
//
//        List<Room> shortestRoute = dijkstra.findShortestRoute(graph, roomA, roomD, new HashSet<>());
//
//        System.out.println("\nDijkstra shortest route from A to D:");
//
//        for (Room room : shortestRoute) {
//            System.out.println(room);
//        }
//
//        Set<Room> avoidRooms = new HashSet<>();
//        avoidRooms.add(roomB);
//
//        List<Room> routeAvoidingB = dijkstra.findShortestRoute(
//                graph,
//                roomA,
//                roomD,
//                avoidRooms
//        );
//
//        System.out.println("\nShortest route avoiding Room B");
//        System.out.println(routeAvoidingB);
//
//
//        List<Room> waypoints = new ArrayList<>();
//        waypoints.add(roomB); // MUST go through Room B!
//        waypoints.add(roomC);
//
//        Set<Room> avoid = new HashSet<>();
//
//        List<Room> routeWithWaypoint = dijkstra.findRouteWithWaypoints(
//                graph,
//                roomA,
//                roomD,
//                waypoints,
//                avoid
//        );
//
//        System.out.println("\n Route with waypoint B:");
//        System.out.println(routeWithWaypoint);
//
//        Set<String> preferredArtists = new HashSet<>();
//        preferredArtists.add("Claude Monet");
//
//        List<Room> interestingRoute = dijkstra.findMostInterestingRoute(
//                graph,
//                roomA,
//                roomD,
//                new HashSet<>(),
//                preferredArtists
//        );
//
//        System.out.println("\nMost interesting route:");
//        System.out.println(interestingRoute);
//
//
//        BufferedImage mapImage = ImageIO.read(new File("map-bw2.png"));
//
//        PixelPoint startPixel = new PixelPoint(445, 108);
//        PixelPoint endPixel = new PixelPoint(1151, 575);
//
//        BFSMapRouteFinder bfs = new BFSMapRouteFinder();
//
//        List<PixelPoint> pixelRoute = bfs.findRoute(
//                mapImage,
//                startPixel,
//                endPixel
//        );
//
//        System.out.println("BFS pixel route length:" + pixelRoute.size());
//        System.out.println(pixelRoute);
//
//
//        System.out.println("Start walkable: " + bfs.isWalkable(mapImage, startPixel));
//        System.out.println("End walkable: " + bfs.isWalkable(mapImage, endPixel));
//
//        ImageRouteDrawer.drawRouteToFile(
//                mapImage,
//                pixelRoute,
//                "bfs-route-output.png"
//        );
//
//        System.out.println("Route image saved as bfs-route-output.png");
//
//
//
//    }
//
//
//}
