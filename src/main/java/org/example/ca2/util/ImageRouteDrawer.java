package org.example.ca2.util;

import org.example.ca2.model.PixelPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageRouteDrawer {

    public static void drawRouteToFile(
            BufferedImage originalImage,
            List<PixelPoint> route,
            String outputFileName
    ) throws IOException {

        BufferedImage outputImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = outputImage.createGraphics();
        g.drawImage(originalImage, 0, 0, null);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));

        for (int i = 0; i < route.size() - 1; i++) {
            PixelPoint current = route.get(i);
            PixelPoint next = route.get(i + 1);

            g.drawLine(
                    current.getX(),
                    current.getY(),
                    next.getX(),
                    next.getY()
            );
        }

        g.dispose();

        ImageIO.write(outputImage, "png", new File(outputFileName));
    }
}