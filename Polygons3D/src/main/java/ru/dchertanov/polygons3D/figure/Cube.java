package ru.dchertanov.polygons3D.figure;

import ru.dchertanov.polygons3D.point.DoublePoint;

import java.util.ArrayList;
import java.util.List;

public class Cube extends Figure {
    private static final double LENGTH = 100;

    public Cube(List<DoublePoint> points, List<List<Integer>> planes) {
        super(points, planes);
    }

    public Cube() {
        // top plane
        points.add(new DoublePoint(LENGTH / 2.0, LENGTH / 2.0, LENGTH / 2.0));
        points.add(new DoublePoint(LENGTH / (-2.0), LENGTH / 2.0, LENGTH / 2.0));
        points.add(new DoublePoint(LENGTH / (-2.0), LENGTH / 2.0, LENGTH / (-2.0)));
        points.add(new DoublePoint(LENGTH / 2.0, LENGTH / 2.0, LENGTH / (-2.0)));
        // bottom plane
        points.add(new DoublePoint(LENGTH / 2.0, LENGTH / (-2.0), LENGTH / 2.0));
        points.add(new DoublePoint(LENGTH / (-2.0), LENGTH / (-2.0), LENGTH / 2.0));
        points.add(new DoublePoint(LENGTH / (-2.0), LENGTH / (-2.0), LENGTH / (-2.0)));
        points.add(new DoublePoint(LENGTH / 2.0, LENGTH / (-2.0), LENGTH / (-2.0)));

        // top plane
        planes.add(List.of(0, 1, 2, 3));
        //bottom plane
        planes.add(List.of(4, 5, 6, 7));
        // back plane
        planes.add(List.of(0, 1, 5, 4));
        // front plane
        planes.add(List.of(2, 3, 7, 6));
        // right plane
        planes.add(List.of(0, 3, 7, 4));
        // left plane
        planes.add(List.of(1, 2, 6, 5));
    }

    @Override
    public Figure clone(Figure figure) {
        Figure newFigure = new Cube();
        newFigure.points = new ArrayList<>(figure.points);
        newFigure.planes = new ArrayList<>(figure.planes);

        return newFigure;
    }
}
