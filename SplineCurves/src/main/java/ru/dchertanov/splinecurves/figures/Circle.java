package ru.dchertanov.splinecurves.figures;

import ru.dchertanov.splinecurves.util.points.Point;

public class Circle extends Figure {
    protected Circle() {
    }

    @Override
    public void generatePixels() {
        prepareEnvironment();

        int r = (int) Point.getDistance(localStartPoint, localEndPoint);
        generatePixelsWithRadius(r);
    }

    public void generatePixelsWithRadius(int r) {
        prepareEnvironment();

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

    /**
     * Add given point (and its analogs for other quadrants) to list
     */
    private void addPointsToList(int x, int y, int x0, int y0) {
        pixels.add(new Point(x0 + x, y0 + y));
        pixels.add(new Point(x0 + x, y0 - y));
        pixels.add(new Point(x0 - x, y0 - y));
        pixels.add(new Point(x0 - x, y0 + y));
    }
}
