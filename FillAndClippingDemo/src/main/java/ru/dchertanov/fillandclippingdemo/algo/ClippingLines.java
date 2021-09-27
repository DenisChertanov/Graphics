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

    /**
     * Method fill given lines with different colors (depending on type) <p>
     * - GREEN - line 100% inside rectangle <p>
     * - PURPLE - line 100% outside rectangle <p>
     * - BLUE - need to check more details
     */
    public static void showLineTypes(List<Line> lines, Rectangle rectangle, PixelatedCanvas canvas) {
        for (Figure line : lines) {
            int startPointCode = line.getStartPoint().getCohenSutherlandCode(rectangle);
            int endPointCode = line.getEndPoint().getCohenSutherlandCode(rectangle);

            int rgb;
            if (startPointCode == 0 && endPointCode == 0) { // inside
                rgb = PixelatedCanvas.getRGBFromColor(Color.GREEN);
            } else if ((startPointCode & endPointCode) != 0) { // outside
                rgb = PixelatedCanvas.getRGBFromColor(Color.PURPLE);
            } else { // need check
                rgb = PixelatedCanvas.getRGBFromColor(Color.BLUE);
            }
            line.drawFigure(rgb, canvas);
        }
    }

    /**
     * Algo which clipping all segments of given lines, which not inside rectangle (use Cohen-Sutherland algo)
     */
    public static void clipping(List<Line> lines, Rectangle rectangle, PixelatedCanvas canvas) {
        int LEFT = 1, RIGHT = 2, TOP = 4, BOTTOM = 8; // codes for different sides of rectangle

        for (Line line : lines) {
            line.drawFigure(PixelatedCanvas.getRGBFromColor(Color.WHITE), canvas); // remove line from canvas

            Point a = new Point(line.getStartPoint());
            Point b = new Point(line.getEndPoint());
            int aCode = a.getCohenSutherlandCode(rectangle);
            int bCode = b.getCohenSutherlandCode(rectangle);

            while (aCode != 0 || bCode != 0) {
                if ((aCode & bCode) != 0) { // check if line outside rectangle
                    break;
                }

                // get point with not zero code
                Point currentPoint = a;
                int currentPointCode = aCode;
                if (aCode == 0) {
                    currentPoint = b;
                    currentPointCode = bCode;
                }

                // find intersection with rectangle side (then replace currentPoint to it)
                double m = (a.getY() - b.getY()) / (double) (a.getX() - b.getX());
                if ((currentPointCode & LEFT) != 0) {
                    currentPoint.setY((int) Math.round((currentPoint.getY() + m * (rectangle.getMinX() - currentPoint.getX()))));
                    currentPoint.setX(rectangle.getMinX());
                } else if ((currentPointCode & RIGHT) != 0) {
                    currentPoint.setY((int) Math.round((currentPoint.getY() + m * (rectangle.getMaxX() - currentPoint.getX()))));
                    currentPoint.setX(rectangle.getMaxX());
                } else if ((currentPointCode & TOP) != 0) {
                    currentPoint.setX((int) Math.round((currentPoint.getX() + 1.0 / m * (rectangle.getMinY() - currentPoint.getY()))));
                    currentPoint.setY(rectangle.getMinY());
                } else if ((currentPointCode & BOTTOM) != 0) {
                    currentPoint.setX((int) Math.round((currentPoint.getX() + 1.0 / m * (rectangle.getMaxY() - currentPoint.getY()))));
                    currentPoint.setY(rectangle.getMaxY());
                }

                aCode = a.getCohenSutherlandCode(rectangle);
                bCode = b.getCohenSutherlandCode(rectangle);
            }

            if ((aCode & bCode) == 0) {
                canvas.drawLine(a.getScaledPoint(), b.getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.GREEN)); // draw line inside rectangle
            }
        }

        rectangle.drawFigure(PixelatedCanvas.getRGBFromColor(Color.RED), canvas); // draw rectangle's border
    }
}
