package ru.dchertanov.fillandclippingdemo.figures;

public class Rectangle extends Figure {
    protected Rectangle() {}

    @Override
    public void generatePixels() {
        prepareEnvironment();
        Figure line = Figure.getInstance("line");
        generateLine(startPoint.getX(), startPoint.getY(), startPoint.getX(), endPoint.getY(), line);
        generateLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), startPoint.getY(), line);
        generateLine(startPoint.getX(), endPoint.getY(), endPoint.getX(), endPoint.getY(), line);
        generateLine(endPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(), line);
    }

    private void generateLine(int x1, int y1, int x2, int y2, Figure line) {
        line.setFigureStartPoint(x1, y1);
        line.setFigureEndPoint(x2, y2);
        line.generatePixels();
        pixels.addAll(line.getPixels());
    }

    public int getMinX() {
        return Integer.min(localStartPoint.getX(), localEndPoint.getX());
    }

    public int getMaxX() {
        return Integer.max(localStartPoint.getX(), localEndPoint.getX());
    }

    public int getMinY() {
        return Integer.min(localStartPoint.getY(), localEndPoint.getY());
    }

    public int getMaxY() {
        return Integer.max(localStartPoint.getY(), localEndPoint.getY());
    }
}
