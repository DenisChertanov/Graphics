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

    public static void fillByHorizontalLines(PixelatedCanvas canvas, List<Edge> edges, int fillColor) {
        findMinAndMaxY(edges);
        List<Integer> currentIntersections = new ArrayList<>();
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
                currentIntersections.add(edge.getIntersectionWithLine(y));
            }

            currentIntersections.sort(Integer::compareTo);
            for (int i = 1; i < currentIntersections.size(); i += 2) {
                canvas.fillHorizontalLine(currentIntersections.get(i - 1), currentIntersections.get(i), y, fillColor);
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
