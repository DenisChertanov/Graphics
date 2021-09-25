package ru.dchertanov.fillandclippingdemo.figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
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
            default:
                throw new IllegalStateException("Unexpected value: " + figureName);
        }
        return result;
    }

    /**
     * Method with implementation of Bresenham's algo for drawing figure
     */
    public abstract void generatePixels();

    /**
     * Method which paints the previously drawn figure on canvas
     *
     * @param gc    graphics context from canvas
     * @param width width of figure's borderline
     * @param color color of figure
     */
    public abstract void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, Color color);

    public void setFigureStartPoint(int x, int y) {
        startPoint = new Point(x, y);
        endPoint = new Point(x, y);
    }

    public void drawCustomFigureByEndPoint(int x, int y, PixelatedCanvas canvas) {
        removePreviousFigureFromCanvasByLib(canvas.getGraphicsContext2D(), 8, Color.WHITE);
        endPoint = new Point(x, y);
        generatePixels();
        canvas.drawByPixels(pixels);
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
}
