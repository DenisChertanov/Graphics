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
    protected final BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

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

    /**
     * Method which draw figure with given fill color on BufferedImage by built-in libraries
     *
     * @param color fill color
     */
    public abstract void drawOnBufferedImage(java.awt.Color color);

    /**
     * Method which draw figure on canvas by list of points.
     * Coordinates multiplied by 2 (pixels has width and height 2 for greater clarity with zoom)
     * @param gc GraphicsContext of canvas
     */
    private void drawOnCanvasByPolygons(GraphicsContext gc) {
        if (pixels.isEmpty())
            return;

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
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
        drawOnCanvasByPolygons(gc);
    }

    /**
     * Method for drawing on canvas by built-in libs.
     * Figure drawing on BufferedImage, then carried over to canvas.
     * @param gc GraphicsContext of canvas
     */
    public void fillRepeatingCanvas(GraphicsContext gc) {
        drawOnBufferedImage(java.awt.Color.WHITE);

        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                double r = ((rgb & (0x00ff0000)) >> 16) / 255.0;
                double g = ((rgb & (0x0000ff00)) >> 8) / 255.0;
                double b = (rgb & (0x000000ff)) / 255.0;

                if (r == 0 || g == 0 || b == 0)
                    continue;

                gc.setFill(Color.BLACK);
                gc.fillRect(x << 1, y << 1, 2, 2);
            }
        }
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
