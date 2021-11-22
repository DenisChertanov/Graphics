package ru.dchertanov.polygons3D.drawer;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import ru.dchertanov.polygons3D.figure.Cube;
import ru.dchertanov.polygons3D.figure.Figure;
import ru.dchertanov.polygons3D.point.DoublePoint;
import ru.dchertanov.polygons3D.point.IntPoint;

public class CanvasDrawer {
    public Figure figure = new Cube();

    public void drawFigure(Canvas canvas, Color borderColor) {
        Figure changedFigure = figure.applyChanges();

        for (var plane : changedFigure.getPlanes()) {
            for (int index = 0; index < plane.size(); ++index) {
                int nextIndex = (index + 1) % plane.size();
                drawLineWithCentralization(canvas, borderColor,
                        changedFigure.getPoint(plane.get(index)), changedFigure.getPoint(plane.get(nextIndex)));
            }
        }
    }

    private void drawLineWithCentralization(Canvas canvas, Color borderColor, DoublePoint a, DoublePoint b) {
        IntPoint startPoint = new IntPoint((int) (a.getX() + (canvas.getHeight() / 2)),
                (int) (-1 * a.getY() + (canvas.getHeight() / 2)),
                0);
        IntPoint endPoint = new IntPoint((int) (b.getX() + (canvas.getHeight() / 2)),
                (int) (-1 * b.getY() + (canvas.getHeight() / 2)),
                0);

        var graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(borderColor);
        graphicsContext.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }
}
