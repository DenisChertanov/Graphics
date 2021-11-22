package ru.dchertanov.polygons3D.point;

public class IntPoint {
    private final int x;
    private final int y;
    private final int z;

    public IntPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public IntPoint(IntPoint intPoint) {
        x = intPoint.x;
        y = intPoint.y;
        z = intPoint.z;
    }

    public IntPoint(DoublePoint doublePoint) {
        x = (int) doublePoint.getX();
        y = (int) doublePoint.getY();
        z = (int) doublePoint.getZ();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
