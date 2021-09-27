package ru.dchertanov.fillandclippingdemo.algo;

import javafx.scene.paint.Color;
import ru.dchertanov.fillandclippingdemo.figures.Figure;
import ru.dchertanov.fillandclippingdemo.figures.Line;
import ru.dchertanov.fillandclippingdemo.figures.Rectangle;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.util.List;

public class ClippingLines {
    private ClippingLines() {

    }

    public static void showLineTypes(List<Line> lines, Rectangle rectangle, PixelatedCanvas canvas) {
        for (Figure line : lines) {
            int startOutCode = line.getStartPoint().getOutCode(rectangle);
            int endOutCode = line.getEndPoint().getOutCode(rectangle);

            int rgb;
            if (startOutCode == 0 && endOutCode == 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.GREEN);
            } else if ((startOutCode & endOutCode) != 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.PURPLE);
            } else {
                rgb = PixelatedCanvas.getRGBFromColor(Color.BLUE);
            }
            line.drawFigure(rgb, canvas);
        }
    }

    public static void clipping(List<Line> lines, Rectangle rectangle, PixelatedCanvas canvas) {
        int left = 1, right = 2, top = 4, bottom = 8;

        for (Line line : lines) {
            line.drawFigure(PixelatedCanvas.getRGBFromColor(Color.WHITE), canvas); // стереть предыдущую
            Point a = new Point(line.getStartPoint());
            Point b = new Point(line.getEndPoint());
            int startOutCode = a.getOutCode(rectangle);
            int endOutCode = b.getOutCode(rectangle);

            while (startOutCode != 0 || endOutCode != 0) {
                if ((startOutCode & endOutCode) != 0) { // отрезок целиком вне прямоугольника
                    break;
                }

                // Взять точку с ненулевым кодом
                Point currentPoint;
                int currentPointOutCode;
                if (startOutCode != 0) {
                    currentPoint = a;
                    currentPointOutCode = startOutCode;
                } else {
                    currentPoint = b;
                    currentPointOutCode = endOutCode;
                }

                double m = (a.getY() - b.getY()) / (double) (a.getX() - b.getX());
                if ((currentPointOutCode & left) != 0) {
                    currentPoint.setY((int) Math.round((currentPoint.getY() + m * (rectangle.getMinX() - currentPoint.getX()))));
                    currentPoint.setX(rectangle.getMinX());
                } else if ((currentPointOutCode & right) != 0) {
                    currentPoint.setY((int) Math.round((currentPoint.getY() + m * (rectangle.getMaxX() - currentPoint.getX()))));
                    currentPoint.setX(rectangle.getMaxX());
                } else if ((currentPointOutCode & top) != 0) {
                    currentPoint.setX((int) Math.round((currentPoint.getX() + 1.0 / m * (rectangle.getMinY() - currentPoint.getY()))));
                    currentPoint.setY(rectangle.getMinY());
                } else if ((currentPointOutCode & bottom) != 0){
                    currentPoint.setX((int) Math.round((currentPoint.getX() + 1.0 / m * (rectangle.getMaxY() - currentPoint.getY()))));
                    currentPoint.setY(rectangle.getMaxY());
                }

                startOutCode = a.getOutCode(rectangle);
                endOutCode = b.getOutCode(rectangle);
            }

            if ((startOutCode & endOutCode) == 0) {
                canvas.drawLine(a.getScaledPoint(), b.getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.GREEN));
            }
        }

        rectangle.drawFigure(PixelatedCanvas.getRGBFromColor(Color.RED), canvas);
    }
}
