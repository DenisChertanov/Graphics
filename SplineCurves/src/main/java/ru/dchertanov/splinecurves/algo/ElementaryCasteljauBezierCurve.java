package ru.dchertanov.splinecurves.algo;

import ru.dchertanov.splinecurves.util.points.DoublePoint;
import ru.dchertanov.splinecurves.util.graphics.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.points.Point;

import java.util.ArrayList;
import java.util.List;

public final class ElementaryCasteljauBezierCurve {
    private static final double DELTA_T = 0.01;

    private ElementaryCasteljauBezierCurve() {
    }

    public static void drawBezierCurve(List<Point> points, int colorRGB, PixelatedCanvas canvas) {
        if (points.isEmpty())
            return;

        List<Point> pixels = new ArrayList<>();

        for (double t = 0; t <= 1.0; t += DELTA_T) {
            pixels.add(getNextCurvePoint(DoublePoint.transformNonDoublePoints(points), t));
        }

        canvas.drawCurveLineByPoints(pixels, colorRGB);
    }

    private static Point getNextCurvePoint(List<DoublePoint> points, double t) {
        if (points.size() == 1) {
            return new Point(points.get(0));
        }

        List<DoublePoint> nextPoints = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; ++i) {
            nextPoints.add(DoublePoint.getRatioPointOnLine(points.get(i), points.get(i + 1), t));
        }
        return getNextCurvePoint(nextPoints, t);
    }
}
