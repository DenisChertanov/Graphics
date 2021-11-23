package ru.dchertanov.splinecurves.algo;

import ru.dchertanov.splinecurves.util.Combinations;
import ru.dchertanov.splinecurves.util.graphics.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.points.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ElementaryMatrixBezierCurve {
    private static final double DELTA_T = 0.01;

    private ElementaryMatrixBezierCurve() {
    }

    /**
     * Method calculates pixels on curve (by matrix algo), draws its on given canvas
     *
     * @param points   list of reference points
     * @param colorRGB color for curve
     * @param canvas
     */
    public static void drawBezierCurve(List<Point> points, int colorRGB, PixelatedCanvas canvas) {
        List<Point> pixels = new ArrayList<>();

        for (double t = 0; t < 1.01; t += DELTA_T) {
            int x = getNextCoordinate(points, t, Point::getX);
            int y = getNextCoordinate(points, t, Point::getY);
            pixels.add(new Point(x, y));
        }

        canvas.drawCurveLineByPoints(pixels, colorRGB);
    }

    /**
     * Method calculates coordinates of next point on curve <p>
     * x(t) = Cn0 * t^0 * (1-t)^n * x0 + Cn1 * t^1 * (1-t)^(n-1) * x1 + Cn2 * t^2 * (1-t)^(n-2) * x2 + ... + Cnn * t^n * (1-t)^0 * xn
     *
     * @param points             list of reference points
     * @param t                  ratio
     * @param coordinateFunction function for getting needed coordinate from point
     * @return X or Y coordinate for next point on curve
     */
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
