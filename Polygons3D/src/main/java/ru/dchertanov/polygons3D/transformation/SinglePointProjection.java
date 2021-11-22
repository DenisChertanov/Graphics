package ru.dchertanov.polygons3D.transformation;

import ru.dchertanov.polygons3D.figure.Figure;
import ru.dchertanov.polygons3D.point.DoublePoint;
import ru.dchertanov.polygons3D.util.Matrix;

public class SinglePointProjection {
    private double focusDistance;

    public SinglePointProjection(double focusDistance) {
        this.focusDistance = focusDistance;
    }

    public Figure applySinglePointProjectionToFigure(Figure figure) {
        Matrix shiftMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, -1.0 / focusDistance},
                {0, 0, 0, 1}
        });

        for (int index = 0; index < figure.getPoints().size(); ++index) {
            var point = figure.getPoint(index);
            Matrix pointMatrix = point.getMatrixWithExtraCoordinate();

            DoublePoint newPoint = pointMatrix.multiply(shiftMatrix).extractPoint();
            newPoint.divH();
            figure.setPoint(index, newPoint);
        }

        return figure;
    }

    public double getFocusDistance() {
        return focusDistance;
    }

    public void setFocusDistance(double focusDistance) {
        this.focusDistance = focusDistance;
    }
}
