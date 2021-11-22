package ru.dchertanov.polygons3D.util;

import ru.dchertanov.polygons3D.point.DoublePoint;

public class Matrix {
    private final double[][] value;

    public Matrix(double[][] value) {
        this.value = value;
    }

    // work only with right matrix
    public Matrix multiply(Matrix matrix) {
        double[][] res = new double[value.length][];
        for (int row = 0; row < value.length; ++row) {
            res[row] = new double[value[row].length];
            for (int column = 0; column < matrix.value[row].length; ++column) {
                double sum = 0;
                for (int i = 0; i < value[row].length; ++i) {
                    sum += value[row][i] * matrix.value[i][column];
                }
                res[row][column] = sum;
            }
        }

        return new Matrix(res);
    }

    public DoublePoint extractPoint() {
        return new DoublePoint(value[0][0], value[0][1], value[0][2], value[0][3]);
    }
}
