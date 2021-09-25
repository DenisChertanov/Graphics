package ru.dchertanov.drawdemoapp.figures;

import javafx.scene.canvas.GraphicsContext;
import ru.dchertanov.drawdemoapp.util.Point;

import java.awt.*;

public class Line extends Figure {
    @Override
    public void generatePixels() {
        prepareEnvironment();

        int x = localStartPoint.getX(), y = localStartPoint.getY();
        int deltaX = Math.abs(localEndPoint.getX() - localStartPoint.getX());
        int deltaY = Math.abs(localEndPoint.getY() - localStartPoint.getY());
        int s1 = Integer.signum(localEndPoint.getX() - localStartPoint.getX()); // +1 or -1 by X which may simply add to variable which changes by 1 modulo each step
        int s2 = Integer.signum(localEndPoint.getY() - localStartPoint.getY()); // +1 or -1 by X which may simply add to variable which changes by 1 modulo each step

        boolean isPointsSwapped = false;
        if (deltaY > deltaX) { // handle different octant
            int tmp = deltaX;
            deltaX = deltaY;
            deltaY = tmp;
            isPointsSwapped = true;
        }

        int error = 2 * deltaY - deltaX, step = 1;
        while (step <= deltaX) {
            pixels.add(new Point(x, y));

            if (error >= 0) {
                if (isPointsSwapped) {
                    x += s1;
                } else {
                    y += s2;
                }

                error -= 2 * deltaX;
            }

            if (isPointsSwapped) {
                y += s2;
            } else {
                x += s1;
            }

            error += 2 * deltaY;
            step++;
        }
    }

    @Override
    public void drawOnBufferedImage(Color color) {
        Point localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        Point localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.drawLine(localStartPoint.getX(), localStartPoint.getY(), localEndPoint.getX(), localEndPoint.getY());
    }

    @Override
    public void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, javafx.scene.paint.Color color) {
        gc.setLineWidth(width);
        gc.setStroke(color);
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }
}
