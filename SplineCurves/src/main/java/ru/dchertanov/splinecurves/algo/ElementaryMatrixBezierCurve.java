package ru.dchertanov.splinecurves.algo;

import ru.dchertanov.splinecurves.util.Combinations;
import ru.dchertanov.splinecurves.util.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ElementaryMatrixBezierCurve {
    private static final double DELTA_T = 0.01;

    private ElementaryMatrixBezierCurve() {
    }

    public static void drawBezierCurve(List<Point> points, int colorRGB, PixelatedCanvas canvas) {
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
        double powT = 1;
        double powNotT = Math.pow(1 - t, points.size() - 1);
        for (int i = 0; i < points.size(); ++i) {
            coordinate += Combinations.getCombinations(points.size() - 1, i) * powT * powNotT * coordinateFunction.apply(points.get(i));
            powT *= t;
            powNotT /= (1 - t);
        }

        return (int) coordinate;
    }
}
