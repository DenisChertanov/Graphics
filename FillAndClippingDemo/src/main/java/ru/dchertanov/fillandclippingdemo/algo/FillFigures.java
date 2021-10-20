package ru.dchertanov.fillandclippingdemo.algo;

import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.util.Stack;
import java.util.function.IntUnaryOperator;

public class FillFigures {
    private static int previousColor;
    private static PixelatedCanvas canvas;

    private FillFigures() {}

    /**
     * Algo which fills every region's row (fills all neighbor pixels with same color as given point)
     */
    public static void fillByRowsWithPoint(PixelatedCanvas canvas, int fillColor, Point point) {
        canvas.clearFillUsed();
        previousColor = canvas.getPixelRGB(point.getX(), point.getY());
        FillFigures.canvas = canvas;

        Stack<Point> stack = new Stack<>();
        stack.push(point);
        while (!stack.empty()) {
            Point currentPoint = stack.pop();
            if (canvas.isPixelFilled(currentPoint.getX(), currentPoint.getY()))
                continue;

            int y = currentPoint.getY();
            int x = currentPoint.getX();
            int xStart = getEndOfSegment(x, y, -1, val -> val - 1) + 1;
            int xEnd = getEndOfSegment(x, y, (int) canvas.getWidth() / 2, val -> val + 1) - 1;
            canvas.fillHorizontalLine(xStart, xEnd, y, fillColor);
            handleNeighborRow(xStart, xEnd, y - 1, stack);
            handleNeighborRow(xStart, xEnd, y + 1, stack);
        }
    }

    /**
     * Method finds all sections for filling in neighbor raw
     */
    private static void handleNeighborRow(int xStart, int xEnd, int y, Stack<Point> stack) {
        if (!canvas.isPixelInsideCanvas(xStart, y))
            return;

        int x = xStart;
        while (x <= xEnd) {
            if (canvas.getPixelRGB(x, y) == previousColor) {
                while (x + 1 <= xEnd && canvas.getPixelRGB(x + 1, y) == previousColor) {
                    x++;
                }
                stack.push(new Point(x, y));
            }
            x++;
        }
    }

    private static int getEndOfSegment(int x, int y, int xEnd, IntUnaryOperator operator) {
        while (x != xEnd && canvas.getPixelRGB(x, y) == previousColor) {
            x = operator.applyAsInt(x);
        }
        return x;
    }
}
