package ru.dchertanov.splinecurves.util.points;

import java.util.ArrayList;
import java.util.List;

public class DoublePoint {
    private double x;
    private double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoublePoint(Point point) {
        x = point.getX();
        y = point.getY();
    }

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param ratio
     * @return
     */
    public static DoublePoint getRatioPointOnLine(DoublePoint startPoint, DoublePoint endPoint, double ratio) {
        return new DoublePoint(
                startPoint.getX() + (endPoint.getX() - startPoint.getX()) * ratio,
                startPoint.getY() + (endPoint.getY() - startPoint.getY()) * ratio
        );
    }

    public static List<DoublePoint> transformNonDoublePoints(List<Point> points) {
        List<DoublePoint> doublePoints = new ArrayList<>();
        for (var point : points) {
            doublePoints.add(new DoublePoint(point));
        }

        return doublePoints;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
