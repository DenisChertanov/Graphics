package ru.dchertanov.splinecurves.algo;

import ru.dchertanov.splinecurves.util.Combinations;
import ru.dchertanov.splinecurves.util.graphics.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.points.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ElementaryMatrixBSpline {
    private static final double DELTA_T = 0.01;

    private ElementaryMatrixBSpline() {
    }

    public static void drawBezierCurve(List<Point> points, int colorRGB, PixelatedCanvas canvas) {
        if (points.size() != 4)
            return;

        List<Point> pixels = new ArrayList<>();

        for (double t = 0; t <= 1.0; t += DELTA_T) {
            int x = getNextCoordinate(points, t, Point::getX);
            int y = getNextCoordinate(points, t, Point::getY);
            pixels.add(new Point(x, y));
        }

        canvas.drawCurveLineByPoints(pixels, colorRGB);
    }

    private static int getNextCoordinate(List<Point> points, double t, Function<Point, Integer> coordinateFunction) {
        double coordinate = 0.0;

        coordinate += Math.pow((1 - t), 3) * coordinateFunction.apply(points.get(0));
        coordinate += (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 4) * coordinateFunction.apply(points.get(1));
        coordinate += ((-3) * Math.pow(t, 3) + 3 * Math.pow(t, 2) + 3 * t + 1) * coordinateFunction.apply(points.get(2));
        coordinate += Math.pow(t, 3) * coordinateFunction.apply(points.get(3));
        coordinate *= 1.0 / 6.0;

        return (int) coordinate;
    }
}
