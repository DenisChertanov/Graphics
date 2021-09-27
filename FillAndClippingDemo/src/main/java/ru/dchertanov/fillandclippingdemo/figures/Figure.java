package ru.dchertanov.fillandclippingdemo.figures;

import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    protected Point startPoint;
    protected Point endPoint;
    protected Point localStartPoint;
    protected Point localEndPoint;
    protected List<Point> pixels = new ArrayList<>();

    public static Figure getInstance(String figureName) {
        Figure result;
        switch (figureName) {
            case ("line"):
                result = new Line();
                break;
            case ("circle"):
                result = new Circle();
                break;
            case ("ellipse"):
                result = new Ellipse();
                break;
            case ("rectangle"):
                result = new Rectangle();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + figureName);
        }
        return result;
    }

    /**
     * Method with implementation of Bresenham's algo for drawing figure
     */
    public abstract void generatePixels();

    public void setFigureStartPoint(int x, int y) {
        startPoint = new Point(x, y);
        endPoint = new Point(x, y);
    }

    public void setFigureEndPoint(int x, int y) {
        endPoint = new Point(x, y);
    }

    public void drawCustomFigureByEndPoint(int x, int y, boolean endOfDrawing, int borderRGB, PixelatedCanvas canvas) {
        canvas.removePreviousPixels(pixels);
        endPoint = new Point(x, y);
        generatePixels();
        canvas.drawByPixels(pixels, endOfDrawing, borderRGB);
    }

    public void drawFigure(int borderRGB, PixelatedCanvas canvas) {
        canvas.drawByPixels(pixels, true, borderRGB);
    }

    /**
     * Method which initialize local point (divide by 2 for scaling)
     */
    protected void prepareEnvironment() {
        startPoint.validate();
        endPoint.validate();

        localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);
        pixels.clear();
    }

    public List<Point> getPixels() {
        return pixels;
    }

    public Point getStartPoint() {
        return localStartPoint;
    }

    public Point getEndPoint() {
        return localEndPoint;
    }
}
