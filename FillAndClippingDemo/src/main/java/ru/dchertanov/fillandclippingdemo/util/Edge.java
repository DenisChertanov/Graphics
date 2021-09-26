package ru.dchertanov.fillandclippingdemo.util;

import ru.dchertanov.fillandclippingdemo.figures.Figure;

import java.util.*;

public class Edge {
    private Point startPoint;
    private Point endPoint;
    private Point nextEdgePoint;
    private Map<Integer, List<Integer>> xCoordinates;

    public Edge(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        this.endPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);

        Figure line = Figure.getInstance("line");
        line.setFigureStartPoint(startPoint.getX(), startPoint.getY());
        line.setFigureEndPoint(endPoint.getX(), endPoint.getY());
        line.generatePixels();
        xCoordinates = new HashMap<>();
        for (Point point : line.getPixels()) {
            if (!xCoordinates.containsKey(point.getY())) {
                xCoordinates.put(point.getY(), new ArrayList<>());
            }
            xCoordinates.get(point.getY()).add(point.getX());
        }
    }

    public Point getIntersectionWithLine(int y) {
        return new Point(Collections.min(xCoordinates.get(y)), Collections.max(xCoordinates.get(y)));
    }

    public int getMinY() {
        return Math.min(startPoint.getY(), endPoint.getY());
    }

    public int getMaxY() {
        return Math.max(startPoint.getY(), endPoint.getY());
    }

    public Point getMathPointIntersectionWithLine(int y) {
        double result = (startPoint.getX() + (y - startPoint.getY()) *
                (endPoint.getX() - startPoint.getX()) /
                (double) (endPoint.getY() - startPoint.getY()));

        if (Math.abs(startPoint.getY() - endPoint.getY()) >= Math.abs(startPoint.getX() - endPoint.getX())) { // y всегда +=1
            if (isRightSight()) {
                return new Point((int) Math.round(result), (int) Math.round(result));
            } else {
                if (result - (int) result <= 0.5) {
                    return new Point((int) result, (int) result);
                } else {
                    return new Point((int) result + 1, (int) result + 1);
                }
            }
        }

        int left, right;
        if (isRightSight()) {
            right = (int) Math.round(result);
            if (isUpSide()) {
                left = getIntersection(y + 1) + 1;
            } else {
                left = getIntersection(y - 1) + 1;
            }
        } else {
            if (result - (int) result <= 0.5) {
                left = (int) result;
            } else {
                left = (int) result + 1;
            }

            if (isUpSide()) {
                right = getIntersection(y + 1) - 1;
            } else {
                right = getIntersection(y - 1) - 1;
            }
        }
        return new Point(inX(left), inX(right));
    }

    private int inX(int x) {
        return Math.min(Math.max(x, Math.min(startPoint.getX(), endPoint.getX())), Math.max(startPoint.getX(), endPoint.getX()));
    }

    private int getIntersection(int y) {
        double result = (startPoint.getX() + (y - startPoint.getY()) *
                (endPoint.getX() - startPoint.getX()) /
                (double) (endPoint.getY() - startPoint.getY()));

        if (isRightSight()) {
            return (int) Math.round(result);
        } else {
            if (result - (int) result <= 0.5) {
                return (int) result;
            } else {
                return (int) result + 1;
            }
        }
    }

    public boolean isRightSight() {
        return startPoint.getX() <= endPoint.getX();
    }

    public boolean isUpSide() {
        return startPoint.getY() >= endPoint.getY();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getMultEndPoint() {
        return new Point(endPoint.getX() * 2, endPoint.getY() * 2);
    }

    public Point getNextEdgePoint() {
        return nextEdgePoint;
    }

    public void setNextEdgePoint(Point nextEdgePoint) {
        this.nextEdgePoint = new Point(nextEdgePoint.getX() / 2, nextEdgePoint.getY() / 2);
    }
}
