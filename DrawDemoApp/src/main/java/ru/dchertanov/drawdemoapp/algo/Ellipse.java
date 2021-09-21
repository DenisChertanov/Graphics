package ru.dchertanov.drawdemoapp.algo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.dchertanov.drawdemoapp.util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ellipse extends Figure {
    @Override
    public void generatePixels() {
        prepareEnvironment();

        if (localStartPoint.equals(localEndPoint)) {
            return;
        }

        int xr = Math.abs(localStartPoint.getX() - localEndPoint.getX());
        int yr = Math.abs(localStartPoint.getY() - localEndPoint.getY());
        int cx = localStartPoint.getX();
        int cy = localStartPoint.getY();

        boolean isEllipseTurned = false;
        if (xr > yr) {
            isEllipseTurned = true;
            int tmp = xr;
            xr = yr;
            yr = tmp;
        }

        int xr2 = xr * xr;
        int yr2 = yr * yr;
        int x = 0;
        int y = yr;
        int delta = yr2 + xr2 - 2 * yr * xr2;

        int xr2Change = y * xr2;
        int yr2Change = 0;

        while (y >= 0) {
            plotPoints(x, y, cx, cy, isEllipseTurned);

            if (delta < 0 && 2 * delta + 2 * xr2Change - xr2 <= 0) {
                x++;
                yr2Change += yr2;
                delta += 2 * yr2Change + yr2;
            } else if (delta > 0 && 2 * delta - 2 * yr2Change - yr2 > 0) {
                y--;
                xr2Change -= xr2;
                delta -= 2 * xr2Change + xr2;
            } else {
                x++;
                y--;
                yr2Change += yr2;
                xr2Change -= xr2;
                delta += 2 * yr2Change - 2 * xr2Change + yr2 + xr2;
            }
        }
    }

    private void plotPoints(int x, int y, int cx, int cy, boolean isEllipseTurned) {
        if (isEllipseTurned) {
            int tmp = x;
            x = y;
            y = tmp;
        }

        pixels.add(new Point(cx + x, cy + y));
        pixels.add(new Point(cx - x, cy + y));
        pixels.add(new Point(cx - x, cy - y));
        pixels.add(new Point(cx + x, cy - y));
    }

    @Override
    public void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, Color color) {
        int xr = (Math.abs(startPoint.getX() - endPoint.getX()) + 8) << 1;
        int yr = (Math.abs(startPoint.getY() - endPoint.getY()) + 8) << 1;

        gc.setFill(color);
        gc.fillOval(startPoint.getX() - xr / 2.0, startPoint.getY() - yr / 2.0, xr, yr);
    }

    @Override
    public void drawOnBufferedImage(BufferedImage bufferedImage, java.awt.Color color) {
        Point localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        Point localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        int xr = Math.abs(localStartPoint.getX() - localEndPoint.getX()) << 1;
        int yr = Math.abs(localStartPoint.getY() - localEndPoint.getY()) << 1;

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.drawOval(localStartPoint.getX() - xr / 2, localStartPoint.getY() - yr / 2, xr, yr);
    }
}
