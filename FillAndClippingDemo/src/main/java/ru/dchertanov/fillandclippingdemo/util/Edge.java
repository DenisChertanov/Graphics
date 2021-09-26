package ru.dchertanov.fillandclippingdemo.util;

import ru.dchertanov.fillandclippingdemo.figures.Figure;

import java.util.*;

public class Edge {
    private final Point startPoint;
    private final Point endPoint;
    private Point nextEdgePoint;

    public Edge(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        this.endPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);
    }

    public int getIntersectionWithLine(int y) {
        return (int) Math.round(startPoint.getX() + (y - startPoint.getY()) *
                (endPoint.getX() - startPoint.getX()) /
                (double) (endPoint.getY() - startPoint.getY()));
    }

    public int getMinY() {
        return Math.min(startPoint.getY(), endPoint.getY());
    }

    public int getMaxY() {
        return Math.max(startPoint.getY(), endPoint.getY());
    }

    public Point getNextEdgePoint() {
        return nextEdgePoint;
    }

    public void setNextEdgePoint(Point nextEdgePoint, boolean needScale) {
        if (needScale) {
            this.nextEdgePoint = new Point(nextEdgePoint.getX() / 2, nextEdgePoint.getY() / 2);
        } else {
            this.nextEdgePoint = new Point(nextEdgePoint.getX(), nextEdgePoint.getY());
        }
    }

    public static Edge getScaledEdge(Edge edge) {
        return new Edge(new Point(edge.getStartPoint().getX() * 4, edge.getStartPoint().getY() * 4),
                new Point(edge.getEndPoint().getX() * 4, edge.getEndPoint().getY() * 4));
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
