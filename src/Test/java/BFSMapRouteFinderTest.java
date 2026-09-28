import org.example.ca2.algorithms.BFSMapRouteFinder;
import org.example.ca2.model.PixelPoint;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BFSMapRouteFinderTest {

    @Test
    public void whitePixelShouldBeWalkable() {
        BFSMapRouteFinder finder = new BFSMapRouteFinder();

        BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        image.setRGB(0,0, Color.WHITE.getRGB());

        PixelPoint point = new PixelPoint(0,0);

        assertTrue(finder.isWalkable(image,point));
    }

    @Test
    public void blackPixelShouldNotBeWalkable() {
        BFSMapRouteFinder finder = new BFSMapRouteFinder();

        BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, Color.BLACK.getRGB());

        PixelPoint point = new PixelPoint(0, 0);

        assertFalse(finder.isWalkable(image, point));
    }

    @Test
    public void routeShouldBeFoundOnWhiteImage() {
        BFSMapRouteFinder finder = new BFSMapRouteFinder();

        BufferedImage image = new BufferedImage(3,3,BufferedImage.TYPE_INT_RGB);

        // Make imgae white
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        PixelPoint start = new PixelPoint(0, 0);
        PixelPoint end = new PixelPoint(2, 2);

        List<PixelPoint> route = finder.findRoute(image, start, end);

        assertFalse(route.isEmpty());
        assertEquals(start, route.get(0));
        assertEquals(end, route.get(route.size()- 1));
    }
}