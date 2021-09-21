package ru.dchertanov.drawdemoapp.algo;

import javafx.scene.canvas.GraphicsContext;
import ru.dchertanov.drawdemoapp.util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Circle extends Figure {
    @Override
    public void generatePixels() {
        prepareEnvironment();

        int r = (int) Point.getDistance(localStartPoint, localEndPoint);
        int x = 0;
        int y = r;
        int delta = 2 - 2 * r;

        while (y >= 0) {
            plotPoints(x, y, localStartPoint.getX(), localStartPoint.getY());

            if (delta < 0 && 2 * delta + 2 * y - 1 <= 0) {
                x++;
                delta += 2 * x + 1;
            } else if (delta > 0 && 2 * delta - 2 * x - 1 > 0) {
                y--;
                delta -= 2 * y + 1;
            } else {
                x++;
                y--;
                delta += 2 * x - 2 * y + 2;
            }
        }
    }

    @Override
    public void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, javafx.scene.paint.Color color) {
        gc.setFill(color);
        int r = (int) Point.getDistance(startPoint, endPoint) + 8;
        gc.fillOval(startPoint.getX() - r, startPoint.getY() - r, r * 2, r * 2);
    }

    private void plotPoints(int x, int y, int x0, int y0) {
        pixels.add(new Point(x0 + x, y0 + y));
        pixels.add(new Point(x0 + x, y0 - y));
        pixels.add(new Point(x0 - x, y0 - y));
        pixels.add(new Point(x0 - x, y0 + y));
    }

    @Override
    public void drawOnBufferedImage(BufferedImage bufferedImage, Color color) {
        Point localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        Point localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        int r = (int) Point.getDistance(localStartPoint, localEndPoint);
        graphics2D.drawOval(localStartPoint.getX() - r, localStartPoint.getY() - r, r * 2, r * 2);
    }
}
