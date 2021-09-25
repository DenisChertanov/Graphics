package ru.dchertanov.drawdemoapp.figures;

import javafx.scene.canvas.GraphicsContext;
import ru.dchertanov.drawdemoapp.util.Point;

import java.awt.*;

public class Circle extends Figure {
    @Override
    public void generatePixels() {
        prepareEnvironment();

        int r = (int) Point.getDistance(localStartPoint, localEndPoint);
        int x = 0;
        int y = r;
        int delta = 2 - 2 * r;

        while (y >= 0) {
            addPointsToList(x, y, localStartPoint.getX(), localStartPoint.getY());

            if (delta < 0 && 2 * delta + 2 * y - 1 <= 0) { // handle horizontal step
                x++;
                delta += 2 * x + 1;
            } else if (delta > 0 && 2 * delta - 2 * x - 1 > 0) { // handle vertical step
                y--;
                delta -= 2 * y + 1;
            } else { // handle diagonal step
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

    /**
     * Add given point (and its analogs for other quadrants) to list
     */
    private void addPointsToList(int x, int y, int x0, int y0) {
        pixels.add(new Point(x0 + x, y0 + y));
        pixels.add(new Point(x0 + x, y0 - y));
        pixels.add(new Point(x0 - x, y0 - y));
        pixels.add(new Point(x0 - x, y0 + y));
    }

    @Override
    public void drawOnBufferedImage(Color color) {
        Point localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        Point localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        int r = (int) Point.getDistance(localStartPoint, localEndPoint);
        graphics2D.drawOval(localStartPoint.getX() - r, localStartPoint.getY() - r, r * 2, r * 2);
    }
}
