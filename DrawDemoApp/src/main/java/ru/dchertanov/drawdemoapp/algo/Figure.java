package ru.dchertanov.drawdemoapp.algo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.dchertanov.drawdemoapp.util.Point;

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

    public abstract void generatePixels();

    public abstract void removePreviousFigureFromCanvasByLib(GraphicsContext gc, int width, Color color);

    public abstract void drawOnBufferedImage(BufferedImage bufferedImage, java.awt.Color color);

    public void drawOnCanvasByPolygons(GraphicsContext gc, Color color) {
        if (pixels.isEmpty())
            return;

        gc.setStroke(color);
        gc.setFill(color);
        for (Point point : pixels) {
            gc.fillRect(point.getX() << 1, point.getY() << 1, 2, 2);
        }
    }

    public void setFigureStartPoint(int x, int y) {
        startPoint = new Point(x, y);
        endPoint = new Point(x, y);
    }

    public void drawCustomFigureByEndPoint(int x, int y, GraphicsContext gc) {
        removePreviousFigureFromCanvasByLib(gc, 8, Color.WHITE);
        endPoint = new Point(x, y);
        generatePixels();
        drawOnCanvasByPolygons(gc, Color.BLACK);
    }

    public void fillRepeatingCanvas(GraphicsContext gc) {
        BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        drawOnBufferedImage(bufferedImage, java.awt.Color.WHITE);

        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                double r = ((rgb & (0x00ff0000)) >> 4) / 255.0;
                double g = ((rgb & (0x0000ff00)) >> 2) / 255.0;
                double b = (rgb & (0x000000ff)) / 255.0;

                if (r == 0 || g == 0 || b == 0)
                    continue;

                gc.setFill(Color.BLACK);
                gc.fillRect(x << 1, y << 1, 2, 2);
            }
        }
    }

    protected void prepareEnvironment() {
        startPoint.validate();
        endPoint.validate();

        localStartPoint = new Point(startPoint.getX() / 2, startPoint.getY() / 2);
        localEndPoint = new Point(endPoint.getX() / 2, endPoint.getY() / 2);
        pixels.clear();
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
