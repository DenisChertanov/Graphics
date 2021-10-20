package ru.dchertanov.fillandclippingdemo.algo;

import ru.dchertanov.fillandclippingdemo.util.Edge;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;

import java.util.ArrayList;
import java.util.List;

public class FillPolygons {
    private static int yMin;
    private static int yMax;

    private FillPolygons() {
    }

    /**
     * Algo for filling polygons (with calculating intersections point on every line)
     */
    public static void fillByHorizontalLines(PixelatedCanvas canvas, List<Edge> edges, int fillColor) {
        findMinAndMaxY(edges);
        for (int y = yMin; y <= yMax; ++y) {
            List<Integer> intersections = getPolygonIntersectionsWithLine(edges, y);
            for (int i = 1; i < intersections.size(); i += 2) {
                canvas.fillHorizontalLine(intersections.get(i - 1), intersections.get(i), y, fillColor);
            }
        }
    }

    /**
     * Method calculates and sorts intersections of horizontal line with every polygon's edge
     */
    private static List<Integer> getPolygonIntersectionsWithLine(List<Edge> edges, int y) {
        List<Integer> intersections = new ArrayList<>();
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            if (y < edge.getMinY() || y > edge.getMaxY() || edge.isHorizontal())
                continue;

            if (y == edge.getEndPoint().getY()) {
                int nextY = edge.getNextEdgePoint().getY();
                if (edge.getEndPoint().getY() == edge.getNextEdgePoint().getY()) {
                    nextY = edges.get((i + 1) % edges.size()).getNextEdgePoint().getY();
                }

                if (Integer.signum(y - edge.getStartPoint().getY()) != Integer.signum(y - nextY))
                    continue;
            }

            intersections.add(edge.getIntersectionWithHorizontalLine(y));
        }
        intersections.sort(Integer::compareTo);

        return intersections;
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
