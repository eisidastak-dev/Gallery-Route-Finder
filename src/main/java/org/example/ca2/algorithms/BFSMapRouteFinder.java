package org.example.ca2.algorithms;

import org.example.ca2.model.PixelPoint;

import java.awt.image.BufferedImage;
import java.util.*;

public class BFSMapRouteFinder {

    public List<PixelPoint> findRoute(
            BufferedImage image,
            PixelPoint start,
            PixelPoint destination
    ) {
        Queue<PixelPoint> queue = new LinkedList<>();
        Set<PixelPoint> visited = new HashSet<>();
        Map<PixelPoint, PixelPoint> previous = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            PixelPoint current = queue.poll();

            if (current.equals(destination)) {
                return buildPath(previous, start, destination);
            }

            for (PixelPoint neighbour : getNeighbours(current)) {
                if (isInsideImage(image, neighbour)
                    && isWalkable(image, neighbour)
                    && !visited.contains(neighbour)) {
                    visited.add(neighbour);
                    previous.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }

        return new ArrayList<>();
    }

    private List<PixelPoint> getNeighbours(PixelPoint point) {
        List<PixelPoint> neighbours = new ArrayList<>();

        int x = point.getX();
        int y = point.getY();

        neighbours.add(new PixelPoint(x +1, y));
        neighbours.add(new PixelPoint(x -1, y));
        neighbours.add(new PixelPoint(x, y + 1));
        neighbours.add(new PixelPoint(x, y - 1));

        return neighbours;
    }

    private boolean isInsideImage(BufferedImage image, PixelPoint point) {
        return point.getX() >= 0 && point.getY() >= 0 && point.getX() < image.getWidth() && point.getY() < image.getHeight();
    }

    public boolean isWalkable(BufferedImage image, PixelPoint point) {
        int rgb = image.getRGB(point.getX(), point.getY());

        int red = (rgb >> 16) & 255;
        int green = (rgb >> 8) & 255;
        int blue = rgb & 255;

        //Treat mostly white/light pixels as walkable

        return red > 200 && green > 200 && blue > 200;
    }

    private List<PixelPoint> buildPath(
            Map<PixelPoint, PixelPoint> previous,
            PixelPoint start,
            PixelPoint destination
    ) {
        List<PixelPoint> path = new ArrayList<>();
        PixelPoint current = destination;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        if (!path.isEmpty() && path.get(0).equals(start)) {
            return path;
        }

        return new ArrayList<>();
    }
}
