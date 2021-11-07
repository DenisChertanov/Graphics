package ru.dchertanov.splinecurves.algo;

import ru.dchertanov.splinecurves.util.Combinations;
import ru.dchertanov.splinecurves.util.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ElementaryBezierCurve {
    private ElementaryBezierCurve() {
    }

    public static void drawBezierCurve(List<Point> points, int colorRGB, PixelatedCanvas canvas) {
        List<Point> scaledPoints = getScaledPoints(points);
        List<Point> pixels = new ArrayList<>();

        for (double t = 0.0001; t < 1.0; t += 0.0001) {
            int x = getNextCoordinate(scaledPoints, t, Point::getX);
            int y = getNextCoordinate(scaledPoints, t, Point::getY);
            pixels.add(new Point(x, y));
        }

        canvas.drawByPixels(pixels, true, colorRGB);
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

    private static List<Point> getScaledPoints(List<Point> points) {
        List<Point> scaledPoints = new ArrayList<>();
        for (var point : points) {
            scaledPoints.add(new Point(point.getX() / 2, point.getY() / 2));
        }

        return scaledPoints;
    }
}
