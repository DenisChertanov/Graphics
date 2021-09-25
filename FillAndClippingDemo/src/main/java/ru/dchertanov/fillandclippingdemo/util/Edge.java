package ru.dchertanov.fillandclippingdemo.util;

public class Edge {
    private Point startPoint;
    private Point endPoint;

    public Edge(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        this.endPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
