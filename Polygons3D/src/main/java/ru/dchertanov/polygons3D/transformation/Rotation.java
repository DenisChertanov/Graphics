package ru.dchertanov.polygons3D.transformation;

import ru.dchertanov.polygons3D.figure.Figure;
import ru.dchertanov.polygons3D.util.Matrix;

public class Rotation {
    private double xAngle;
    private double yAngle;
    private double zAngle;

    public Rotation(double xAngle, double yAngle, double zAngle) {
        this.xAngle = xAngle;
        this.yAngle = yAngle;
        this.zAngle = zAngle;
    }

    public Figure applyRotationToFigure(Figure figure) {
        Matrix xMatrix = new Matrix(new double[][] {
                {1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(xAngle)), Math.sin(Math.toRadians(xAngle)), 0},
                {0, -Math.sin(Math.toRadians(xAngle)), Math.cos(Math.toRadians(xAngle)), 0},
                {0, 0, 0, 1}
        });
        Matrix yMatrix = new Matrix(new double[][] {
                {Math.cos(Math.toRadians(yAngle)), 0, -Math.sin(Math.toRadians(yAngle)), 0},
                {0, 1, 0, 0},
                {Math.sin(Math.toRadians(yAngle)), 0, Math.cos(Math.toRadians(yAngle)), 0},
                {0, 0, 0, 1}
        });
        Matrix zMatrix = new Matrix(new double[][] {
                {Math.cos(Math.toRadians(zAngle)), Math.sin(Math.toRadians(zAngle)), 0, 0},
                {-Math.sin(Math.toRadians(zAngle)), Math.cos(Math.toRadians(zAngle)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });

        for (int index = 0; index < figure.getPoints().size(); ++index) {
            var point = figure.getPoint(index);
            Matrix pointMatrix = point.getMatrixWithExtraCoordinate();

            Matrix multiply = pointMatrix.multiply(xMatrix);
            multiply = multiply.multiply(yMatrix);
            multiply = multiply.multiply(zMatrix);

            figure.setPoint(index, multiply.extractPoint());
        }

        return figure;
    }

    public double getxAngle() {
        return xAngle;
    }

    public void setxAngle(double xAngle) {
        this.xAngle = xAngle;
    }

    public double getyAngle() {
        return yAngle;
    }

    public void setyAngle(double yAngle) {
        this.yAngle = yAngle;
    }

    public double getzAngle() {
        return zAngle;
    }

    public void setzAngle(double zAngle) {
        this.zAngle = zAngle;
    }
}
