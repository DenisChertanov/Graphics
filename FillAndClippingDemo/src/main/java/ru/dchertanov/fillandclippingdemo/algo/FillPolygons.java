package ru.dchertanov.fillandclippingdemo.algo;

import ru.dchertanov.fillandclippingdemo.util.Edge;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FillPolygons {
    private static PixelatedCanvas canvas;
    private static int yMin;
    private static int yMax;

    private FillPolygons() {
    }

    public static void fillByHorizontalLinesWithHuck(PixelatedCanvas canvas, List<Edge> edges, int fillColor) {
        FillPolygons.canvas = canvas;
        findMinAndMaxY(edges);
        List<Point> currentIntersections = new ArrayList<>();
        for (int y = yMin; y <= yMax; ++y) {
            currentIntersections.clear();
            for (Edge edge : edges) {
                if (y < edge.getMinY() || y > edge.getMaxY())
                    continue;

                Point x = edge.getIntersectionWithLine(y);
                if (y == edge.getEndPoint().getY()) {
                    if (Integer.signum(y - edge.getStartPoint().getY()) != Integer.signum(y - edge.getNextEdgePoint().getY())) {
                        continue;
                    }
                }

                currentIntersections.add(x);
            }

            currentIntersections.sort(Comparator.comparing(Point::getX));
            for (int i = 1; i < currentIntersections.size(); i += 2) {
                canvas.fillHorizontalLine(currentIntersections.get(i - 1).getY() + 1, currentIntersections.get(i).getX() - 1, y, fillColor);
            }
        }
    }

    public static void fillByHorizontalLines(PixelatedCanvas canvas, List<Edge> edges, int fillColor) {
        FillPolygons.canvas = canvas;
        findMinAndMaxY(edges);
        List<Point> currentIntersections = new ArrayList<>();
        for (int y = yMin; y <= yMax; ++y) {
            currentIntersections.clear();
            for (Edge edge : edges) {
                if (y < edge.getMinY() || y > edge.getMaxY())
                    continue;

                if (y == edge.getEndPoint().getY()) {
                    if (Integer.signum(y - edge.getStartPoint().getY()) != Integer.signum(y - edge.getNextEdgePoint().getY())) {
                        continue;
                    }
                }
                currentIntersections.add(edge.getMathPointIntersectionWithLine(y));
            }

            currentIntersections.sort(Comparator.comparing(Point::getX));
            for (int i = 1; i < currentIntersections.size(); i += 2) {
                canvas.fillHorizontalLine(currentIntersections.get(i - 1).getX(), currentIntersections.get(i).getY(), y, fillColor);
            }
        }
    }

    private static void findMinAndMaxY(List<Edge> edges) {
        yMin = edges.get(0).getMinY();
        yMax = edges.get(0).getMaxY();
        for (Edge edge : edges) {
            yMin = Math.min(yMin, edge.getMinY());
            yMax = Math.max(yMax, edge.getMaxY());
        }
    }
}
