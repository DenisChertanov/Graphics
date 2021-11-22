package ru.dchertanov.polygons3D.transformation;

import ru.dchertanov.polygons3D.figure.Figure;
import ru.dchertanov.polygons3D.util.Matrix;

public class Shift {
    private double xShift;
    private double yShift;
    private double zShift;

    public Shift(double xShift, double yShift, double zShift) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.zShift = zShift;
    }

    public Figure applyShiftToFigure(Figure figure) {
        Matrix shiftMatrix = new Matrix(new double[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {xShift, yShift, zShift, 1}
        });

        for (int index = 0; index < figure.getPoints().size(); ++index) {
            var point = figure.getPoint(index);
            Matrix pointMatrix = point.getMatrixWithExtraCoordinate();
            figure.setPoint(index, (pointMatrix.multiply(shiftMatrix)).extractPoint());
        }

        return figure;
    }

    public double getxShift() {
        return xShift;
    }

    public void setxShift(double xShift) {
        this.xShift = xShift;
    }

    public double getyShift() {
        return yShift;
    }

    public void setyShift(double yShift) {
        this.yShift = yShift;
    }

    public double getzShift() {
        return zShift;
    }

    public void setzShift(double zShift) {
        this.zShift = zShift;
    }
}
