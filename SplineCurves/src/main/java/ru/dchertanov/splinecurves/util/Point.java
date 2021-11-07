package ru.dchertanov.splinecurves.util;

public class Point {
    private int x;
    private int y;

    public static double getDistance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
    }

    public void validate() {
        x = Integer.max(x, 0);
        y = Integer.max(y, 0);
    }

    public Point getScaledPoint() {
        return new Point(x * 2, y * 2);
    }

    @Override
    public boolean equals(Object point) {
        if (point instanceof Point) {
            return x == ((Point) point).x && y == ((Point) point).y;
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
