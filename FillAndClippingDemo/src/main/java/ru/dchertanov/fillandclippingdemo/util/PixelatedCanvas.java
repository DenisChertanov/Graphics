package ru.dchertanov.fillandclippingdemo.util;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class PixelatedCanvas extends Canvas {
    private final int canvasSide = 2000;
    private final int pixelSide = 2;
    private final int[][] pixelsColor = new int[canvasSide][canvasSide];
    private final boolean[][] used = new boolean[canvasSide][canvasSide];

    public static int getRGBFromColor(Color color) {
        int r = ((int) color.getRed() * 255);
        int g = ((int) color.getGreen() * 255);
        int b = ((int) color.getBlue() * 255);
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
    public void drawByPixels(List<Point> pixels) {
        clearPixelsColor(); // удалить после добавления 2го канваса!!!
        if (pixels.isEmpty())
            return;

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        int rgb = getRGBFromColor(Color.BLACK);
        for (Point point : pixels) {
            if (!isPixelInsideCanvas(point.getX() * pixelSide, point.getY() * pixelSide))
                continue;

            pixelsColor[point.getY() * pixelSide][point.getX() * pixelSide] = rgb;
            gc.fillRect(point.getX() * pixelSide, point.getY() * pixelSide, pixelSide, pixelSide);
        }
    }

    public void drawPixel(int x, int y, int rgb) {
        pixelsColor[y * pixelSide][x * pixelSide] = rgb;

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(getColorFromRGB(rgb));
        gc.fillRect(x * pixelSide, y * pixelSide, pixelSide, pixelSide);
    }

    public void fillHorizontalLine(int xStart, int xEnd, int y, int rgb) {
        Arrays.fill(pixelsColor[y * pixelSide], xStart * pixelSide, xEnd * pixelSide + pixelSide, rgb);
        Arrays.fill(used[y * pixelSide], xStart * pixelSide, xEnd * pixelSide + pixelSide, true);

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(getColorFromRGB(rgb));
        gc.fillRect(xStart * pixelSide, y * pixelSide, (xEnd - xStart + 1) * pixelSide, pixelSide);
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
        return used[y * pixelSide][x * pixelSide];
    }

    public void clearFillUsed() {
        for (int y = 0; y < canvasSide; ++y) {
            Arrays.fill(used[y], false);
        }
    }

    private void clearPixelsColor() {
        for (int i = 0; i < getWidth(); ++i) {
            Arrays.fill(pixelsColor[i], getRGBFromColor(Color.WHITE));
        }
    }
}
