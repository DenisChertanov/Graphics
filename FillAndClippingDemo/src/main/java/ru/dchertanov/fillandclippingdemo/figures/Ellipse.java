package ru.dchertanov.fillandclippingdemo.figures;

import ru.dchertanov.fillandclippingdemo.util.Point;

public class Ellipse extends Figure {
    protected Ellipse() {}

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
        if (xr > yr) { // if horizontal radius more than vertical radius, swap this (after we will turn figure by 90 degrees)
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

        int xr2Change = y * xr2; // optimization for y * xr2 (y changes only by 1, so instead of multiplication, we can use addition)
        int yr2Change = 0; // optimization for x * yr2 (x changes only by 1, so instead of multiplication, we can use addition)

        while (y >= 0) {
            addPointsToList(x, y, cx, cy, isEllipseTurned);

            if (delta < 0 && 2 * delta + 2 * xr2Change - xr2 <= 0) { // handle horizontal step
                x++;
                yr2Change += yr2;
                delta += 2 * yr2Change + yr2;
            } else if (delta > 0 && 2 * delta - 2 * yr2Change - yr2 > 0) { // handle vertical step
                y--;
                xr2Change -= xr2;
                delta -= 2 * xr2Change + xr2;
            } else { // handle diagonal step
                x++;
                y--;
                yr2Change += yr2;
                xr2Change -= xr2;
                delta += 2 * yr2Change - 2 * xr2Change + yr2 + xr2;
            }
        }
    }

    /**
     * Add given point (and its analogs for other quadrants) to list
     */
    private void addPointsToList(int x, int y, int cx, int cy, boolean isEllipseTurned) {
        if (isEllipseTurned) { // turn ellipse by 90 degrees
            int tmp = x;
            x = y;
            y = tmp;
        }

        pixels.add(new Point(cx + x, cy + y));
        pixels.add(new Point(cx - x, cy + y));
        pixels.add(new Point(cx - x, cy - y));
        pixels.add(new Point(cx + x, cy - y));
    }
}
