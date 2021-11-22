package ru.dchertanov.polygons3D.point;

import ru.dchertanov.polygons3D.util.Matrix;

public class DoublePoint {
    private final double x;
    private final double y;
    private final double z;
    private final double h;

    public DoublePoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = 1;
    }

    public DoublePoint(DoublePoint doublePoint) {
        x = doublePoint.x;
        y = doublePoint.y;
        z = doublePoint.z;
        h = doublePoint.h;
    }

    public DoublePoint(IntPoint intPoint) {
        x = intPoint.getX();
        y = intPoint.getY();
        z = intPoint.getZ();
        h = 1;
    }

    public DoublePoint(double x, double y, double z, double h) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = h;
    }

    public Matrix getMatrixWithExtraCoordinate() {
        return new Matrix(new double[][] {{x, y, z, h}});
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getH() {
        return h;
    }
}
