package ru.dchertanov.splinecurves.util;

import ru.dchertanov.splinecurves.algo.ElementaryMatrixBezierCurve;
import ru.dchertanov.splinecurves.figures.Circle;
import ru.dchertanov.splinecurves.figures.Figure;
import ru.dchertanov.splinecurves.filling.FillFigures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovablePointsHandler {
    private final static double DELTA_PIXELS = 15.0;
    private final static int PATTERN_CURVE_POINTS_NUMBER = 4;
    private final List<Point> points = new ArrayList<>();

    public Optional<Integer> getNearestPoint(Point checkedPoint) {
        for (int i = 0; i < points.size(); ++i) {
            if (Point.getDistance(checkedPoint, points.get(i)) <= DELTA_PIXELS) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    public void drawElementaryBezierCurve(int colorRGB, PixelatedCanvas canvas,
                                          TernaryConsumer<List<Point>, Integer, PixelatedCanvas> drawer) {
//        ElementaryMatrixBezierCurve.drawBezierCurve(points, colorRGB, canvas);
        drawer.apply(points, colorRGB, canvas);
    }

    public void drawCompositeBezierCurve(int colorRGB, PixelatedCanvas canvas,
                                         TernaryConsumer<List<Point>, Integer, PixelatedCanvas> drawer) {
        for (int index = 0; index < points.size(); index += 2) {
            if (index + PATTERN_CURVE_POINTS_NUMBER > points.size()) {
                break;
            }

//            ElementaryMatrixBezierCurve.drawBezierCurve(
//                    List.of(getStartPointForCompositeCurve(index),
//                            points.get(index + 1),
//                            points.get(index + 2),
//                            getEndPointForCompositeCurve(index)),
//                    colorRGB, canvas);
            drawer.apply(
                    List.of(getStartPointForCompositeCurve(index),
                            points.get(index + 1),
                            points.get(index + 2),
                            getEndPointForCompositeCurve(index)),
                    colorRGB, canvas);
        }
    }

    private Point getStartPointForCompositeCurve(int index) {
        if (index == 0) {
            return new Point(points.get(0));
        } else {
            return Point.getMiddlePoint(points.get(index), points.get(index + 1));
        }
    }

    private Point getEndPointForCompositeCurve(int index) {
        if (index + 2 + PATTERN_CURVE_POINTS_NUMBER > points.size()) {
            return new Point(points.get(index + 3));
        } else {
            return Point.getMiddlePoint(points.get(index + 2), points.get(index + 3));
        }
    }

    public void drawBorder(int borderColorRGB, PixelatedCanvas canvas) {
        for (int i = 0; i < points.size() - 1; ++i) {
            Figure line = Figure.getInstance("line");
            line.setFigureStartPoint(points.get(i).getX(), points.get(i).getY());
            line.drawFigureByEndPoint(points.get(i + 1).getX(), points.get(i + 1).getY(), true, borderColorRGB, canvas);
        }
    }

    public void drawPoints(int colorRBG, PixelatedCanvas canvas) {
        for (var point : points) {
            Circle circle = (Circle) Figure.getInstance("circle");
            circle.setFigureStartPoint(point.getX(), point.getY());
            circle.generatePixelsWithRadius(2);
            circle.drawFigure(colorRBG, canvas);
            FillFigures.fillByRowsWithPoint(canvas, colorRBG, circle.getStartPoint());
        }
    }

    public void replacePoint(int index, Point point) {
        points.get(index).setX(point.getX());
        points.get(index).setY(point.getY());
    }

    public void addPoint(Point point) {
        points.add(new Point(point.getX(), point.getY()));
    }

    public Point getPoint(int index) {
        return new Point(points.get(index));
    }

    public void closeCompositeCurve() {
        if (points.size() < 4) {
            return;
        }

        if ((points.size() & 1) != 0) {
            points.remove(points.size() - 1);
        }

        Point lastPoint = points.get(points.size() - 1);
        points.remove(points.size() - 1);
        points.add(Point.getDoubledEndPoint(points.get(points.size() - 1), lastPoint));
        points.add(Point.getDoubledEndPoint(points.get(1), points.get(0)));
        points.add(points.get(0));
    }

    public void clearPoints() {
        points.clear();
    }
}
