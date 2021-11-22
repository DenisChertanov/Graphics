package ru.dchertanov.polygons3D.figure;

import ru.dchertanov.polygons3D.point.DoublePoint;
import ru.dchertanov.polygons3D.transformation.Rotation;
import ru.dchertanov.polygons3D.transformation.Scaling;
import ru.dchertanov.polygons3D.transformation.Shift;
import ru.dchertanov.polygons3D.transformation.SinglePointProjection;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    protected List<DoublePoint> points;
    protected List<List<Integer>> planes;
    public Rotation rotation = new Rotation(0, 0, 0);
    public Shift shift = new Shift(0, 0, 0);
    public Scaling scaling = new Scaling(1, 1, 1, 1);
    public SinglePointProjection singlePointProjection = new SinglePointProjection(500);
    public boolean shiftFirst = true;
    public boolean parallelProjection = true;

    public Figure() {
        points = new ArrayList<>();
        planes = new ArrayList<>();
    }

    public Figure(List<DoublePoint> points, List<List<Integer>> planes) {
        this.points = points;
        this.planes = planes;
    }

    public abstract Figure clone(Figure figure);

    // Figure changing
    public Figure applyChanges() {
        Figure changedFigure = clone(this);
        // Handle scaling
        changedFigure = scaling.applyScalingToFigure(changedFigure);

        // Handle shift/rotation order
        if (shiftFirst) {
            changedFigure = shift.applyShiftToFigure(changedFigure);
            changedFigure = rotation.applyRotationToFigure(changedFigure);
        } else {
            changedFigure = rotation.applyRotationToFigure(changedFigure);
            changedFigure = shift.applyShiftToFigure(changedFigure);
        }

        // Handle projection
        if (!parallelProjection) {
            changedFigure = singlePointProjection.applySinglePointProjectionToFigure(changedFigure);
        }

        return changedFigure;
    }

    public List<List<Integer>> getPlanes() {
        return planes;
    }

    public DoublePoint getPoint(int index) {
        return points.get(index);
    }

    public List<DoublePoint> getPoints() {
        return points;
    }

    public void setPoint(int index, DoublePoint point) {
        points.set(index, point);
    }
}
