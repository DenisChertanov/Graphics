package ru.dchertanov.drawdemoapp.algo;

import javafx.scene.canvas.GraphicsContext;
import ru.dchertanov.drawdemoapp.util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Line extends Figure {
    @Override
    public void generatePixels() {
        prepareEnvironment();

        int currentX = localStartPoint.getX(), currentY = localStartPoint.getY();
        int deltaX = Math.abs(localEndPoint.getX() - localStartPoint.getX());
        int deltaY = Math.abs(localEndPoint.getY() - localStartPoint.getY());
        int s1 = Integer.signum(localEndPoint.getX() - localStartPoint.getX());
        int s2 = Integer.signum(localEndPoint.getY() - localStartPoint.getY());

        boolean isPointsSwapped = false;
        if (deltaY > deltaX) {
            int tmp = deltaX;
            deltaX = deltaY;
            deltaY = tmp;
            isPointsSwapped = true;
        }

        int error = 2 * deltaY - deltaX, step = 1;
        while (step <= deltaX) {
            pixels.add(new Point(currentX, currentY));

            if (error >= 0) {
                if (isPointsSwapped) {
                    currentX += s1;
                } else {
                    currentY += s2;
                }

                error -= 2 * deltaX;
            }

            if (isPointsSwapped) {
                currentY += s2;
            } else {
                currentX += s1;
            }

            error += 2 * deltaY;
            step++;
        }
    }

    @Override
    public void drawOnBufferedImage(BufferedImage bufferedImage, Color color) {
        Point localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        Point localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.drawLine(localStartPoint.getX(), localStartPoint.getY(), localEndPoint.getX(), localEndPoint.getY());
    }

    public void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, javafx.scene.paint.Color color) {
        gc.setLineWidth(width);
        gc.setStroke(color);
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }
}
