package ru.dchertanov.polygons3D.figure;

import ru.dchertanov.polygons3D.point.DoublePoint;

import java.util.ArrayList;
import java.util.List;

public class Tetrahedron extends Figure {
    private static final double LENGTH = 100;

    public Tetrahedron() {
        double hPlane = (Math.sqrt(3) / 2.0) * LENGTH;
        double h = Math.sqrt(2.0 / 3.0) * LENGTH;

        // top
        points.add(new DoublePoint(0, h / 3.0 * 2.0, 0));
        // bottom left
        points.add(new DoublePoint(-(LENGTH / 2.0), -(h / 3.0), -(hPlane / 3.0)));
        // bottom right
        points.add(new DoublePoint(LENGTH / 2.0, -(h / 3.0), -(hPlane / 3.0)));
        // bottom center
        points.add(new DoublePoint(0, -(h / 3.0), hPlane / 3.0 * 2));

        // bottom plane
        planes.add(List.of(1, 2, 3));
        // front plane
        planes.add(List.of(1, 2, 0));
        // left plane
        planes.add(List.of(0, 1, 3));
        // right plane
        planes.add(List.of(0, 2, 3));
    }

    @Override
    public Figure clone(Figure figure) {
        Figure newFigure = new Tetrahedron();
        newFigure.points = new ArrayList<>(figure.points);
        newFigure.planes = new ArrayList<>(figure.planes);

        return newFigure;
    }
}
