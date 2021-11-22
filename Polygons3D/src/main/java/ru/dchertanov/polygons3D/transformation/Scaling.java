package ru.dchertanov.polygons3D.transformation;

import ru.dchertanov.polygons3D.figure.Figure;
import ru.dchertanov.polygons3D.util.Matrix;

public class Scaling {
    private double xScaling;
    private double yScaling;
    private double zScaling;
    private double xyzScaling;

    public Scaling(double xScaling, double yScaling, double zScaling, double xyzScaling) {
        this.xScaling = xScaling;
        this.yScaling = yScaling;
        this.zScaling = zScaling;
        this.xyzScaling = xyzScaling;
    }

    public Figure applyScalingToFigure(Figure figure) {
        Matrix scalingMatrix = new Matrix(new double[][] {
                {xScaling, 0, 0, 0},
                {0, yScaling, 0, 0},
                {0, 0, zScaling, 0},
                {0, 0, 0, 1}
        });

        for (int index = 0; index < figure.getPoints().size(); ++index) {
            var point = figure.getPoint(index);
            Matrix pointMatrix = point.getMatrixWithExtraCoordinate();
            figure.setPoint(index, (pointMatrix.multiply(scalingMatrix)).extractPoint());
        }

        return figure;
    }

    public void setxScaling(double xScaling) {
        this.xScaling = xScaling;
    }

    public void setyScaling(double yScaling) {
        this.yScaling = yScaling;
    }

    public void setzScaling(double zScaling) {
        this.zScaling = zScaling;
    }

    public void setXyzScaling(double xyzScaling) {
        this.xyzScaling = xyzScaling;
    }
}
