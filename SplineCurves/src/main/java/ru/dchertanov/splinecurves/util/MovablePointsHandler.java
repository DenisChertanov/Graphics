package ru.dchertanov.splinecurves.util;

import javafx.scene.paint.Color;
import ru.dchertanov.splinecurves.algo.ElementaryBezierCurve;
import ru.dchertanov.splinecurves.figures.Circle;
import ru.dchertanov.splinecurves.figures.Figure;
import ru.dchertanov.splinecurves.filling.FillFigures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovablePointsHandler {
    private final static double deltaPixels = 15.0;
    private final List<Point> points = new ArrayList<>();

    public Optional<Integer> getNearestPoint(Point checkedPoint) {
        for (int i = 0; i < points.size(); ++i) {
            if (Point.getDistance(checkedPoint, points.get(i)) <= deltaPixels) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    public void drawElementaryBezierCurve(int colorRGB, PixelatedCanvas canvas) {
        ElementaryBezierCurve.drawBezierCurve(points, colorRGB, canvas);
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

    public void clearPoints() {
        points.clear();
    }
}
