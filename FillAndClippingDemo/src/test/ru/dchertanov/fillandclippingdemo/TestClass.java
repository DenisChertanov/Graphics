package ru.dchertanov.fillandclippingdemo;

import org.junit.jupiter.api.Test;
import ru.dchertanov.fillandclippingdemo.util.Edge;
import ru.dchertanov.fillandclippingdemo.util.Point;

public class TestClass {
    @Test
    public void testFillPolygon() {
        Point startPoint = new Point(24 * 2, 9 * 2);
        Point endPoint = new Point(20 * 2, 7 * 2);
        Edge edge = new Edge(startPoint, endPoint);

        int yMin = edge.getMinY();
        int yMax = edge.getMaxY();
        for (int y = yMin; y <= yMax; ++y) {
            Point point = edge.getMathPointIntersectionWithLine(y);
            System.out.println("y = " + y + ", left = " + point.getX() + ", right = " + point.getY());
        }
    }
}
