package ru.dchertanov.fillandclippingdemo.figures;

import ru.dchertanov.fillandclippingdemo.util.Point;

public class Line extends Figure {
    protected Line() {}

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

        int error = 2 * deltaY - deltaX, step = 0;
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
}
