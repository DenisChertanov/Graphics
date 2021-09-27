package ru.dchertanov.fillandclippingdemo.util;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.dchertanov.fillandclippingdemo.figures.Figure;

import java.util.Arrays;
import java.util.List;

public class PixelatedCanvas extends Canvas {
    private final int canvasSide = 2000;
    private final int pixelSide = 2;
    private final int[][] pixelsColor = new int[canvasSide][canvasSide];
    private final boolean[][] usedForFill = new boolean[canvasSide][canvasSide];

    {
        for (int y = 0; y < canvasSide; ++y) {
            Arrays.fill(pixelsColor[y], getRGBFromColor(Color.WHITE));
        }
    }

    public static int getRGBFromColor(Color color) {
        int r = ((int) (color.getRed() * 255));
        int g = ((int) (color.getGreen() * 255));
        int b = ((int) (color.getBlue() * 255));
        return (r << 16) + (g << 8) + b;
    }

    public static Color getColorFromRGB(int rgb) {
        int r = (rgb & (0x00ff0000)) >> 16;
        int g = (rgb & (0x0000ff00)) >> 8;
        int b = rgb & (0x000000ff);

        return Color.rgb(r, g, b);
    }

    public int getPixelRGB(int x, int y) {
        return pixelsColor[y * pixelSide][x * pixelSide];
    }

    /**
     * Method which draw figure on canvas by list of points.
     * Coordinates multiplied by 2 (pixels has width and height 2 for greater clarity with zoom)
     *
     * @param pixels list of pixels
     */
    public void drawByPixels(List<Point> pixels, boolean endOfDrawing, int rgb) {
        if (pixels.isEmpty())
            return;

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(getColorFromRGB(rgb));
        for (Point point : pixels) {
            if (!isPixelInsideCanvas(point.getX() * pixelSide, point.getY() * pixelSide))
                continue;

            if (endOfDrawing) {
                pixelsColor[point.getY() * pixelSide][point.getX() * pixelSide] = rgb;
            }
            gc.fillRect(point.getX() * pixelSide, point.getY() * pixelSide, pixelSide, pixelSide);
        }
    }

    public void removePreviousPixels(List<Point> pixels) {
        if (pixels.isEmpty())
            return;

        GraphicsContext gc = getGraphicsContext2D();
        for (Point point : pixels) {
            if (!isPixelInsideCanvas(point.getX() * pixelSide, point.getY() * pixelSide))
                continue;

            gc.setFill(getColorFromRGB(pixelsColor[point.getY() * pixelSide][point.getX() * pixelSide]));
            gc.fillRect(point.getX() * pixelSide, point.getY() * pixelSide, pixelSide, pixelSide);
        }
    }

    public void fillHorizontalLine(int xStart, int xEnd, int y, int rgb) {
        if (xStart > xEnd)
            return;

        Arrays.fill(pixelsColor[y * pixelSide], xStart * pixelSide, xEnd * pixelSide + pixelSide, rgb);
        Arrays.fill(usedForFill[y * pixelSide], xStart * pixelSide, xEnd * pixelSide + pixelSide, true);

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(getColorFromRGB(rgb));
        gc.fillRect(xStart * pixelSide, y * pixelSide, (xEnd - xStart + 1) * pixelSide, pixelSide);
    }

    public void drawLine(Point startPoint, Point endPoint, int rgb) {
        Figure line = Figure.getInstance("line");
        line.setFigureStartPoint(startPoint.getX(), startPoint.getY());
        line.setFigureEndPoint(endPoint.getX(), endPoint.getY());
        line.generatePixels();
        drawByPixels(line.getPixels(), true, rgb);
    }

    public boolean isPixelInsideCanvas(int x, int y) {
        x *= pixelSide;
        y *= pixelSide;
        return (x >= 0 && x <= canvasSide - pixelSide) && (y >= 0 && y <= canvasSide - pixelSide);
    }

    public void clear() {
        clearPixelsColor();
        getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
    }

    public boolean isPixelFilled(int x, int y) {
        return usedForFill[y * pixelSide][x * pixelSide];
    }

    public void clearFillUsed() {
        for (int y = 0; y < canvasSide; ++y) {
            Arrays.fill(usedForFill[y], false);
        }
    }

    private void clearPixelsColor() {
        for (int i = 0; i < getWidth(); ++i) {
            Arrays.fill(pixelsColor[i], getRGBFromColor(Color.WHITE));
        }
    }
}
