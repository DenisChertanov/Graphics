package ru.dchertanov.splinecurves.util.curve.mode;

import ru.dchertanov.splinecurves.util.MovablePointsHandler;
import ru.dchertanov.splinecurves.util.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.Point;

import java.util.Optional;

public abstract class CurveMode {
    protected MovablePointsHandler movablePointsHandler = new MovablePointsHandler();

    public abstract void removePreviousCurve(int colorRGB, PixelatedCanvas canvas);

    public abstract void drawCurve(int pointColorRGB, int borderColorRGB, int curveColorRGB, PixelatedCanvas canvas);

    public void addPoint(Point point) {
        movablePointsHandler.addPoint(point);
    }

    public void replacePoint(int movingIndex, Point point) {
        movablePointsHandler.replacePoint(movingIndex, point);
    }

    public Optional<Integer> getNearestPoint(Point point) {
        return movablePointsHandler.getNearestPoint(point);
    }

    public void clearPoints() {
        movablePointsHandler.clearPoints();
    }

    public static CurveMode getInstance(CurveModeEnum curveMode) {
        CurveMode instance = null;
        switch (curveMode) {
            case ELEMENTARY:
                instance = new ElementaryCurveMode();
                break;
            case COMPOSITE:
                instance = new CompositeCurveMode();
                break;
        }

        return instance;
    }

    public enum CurveModeEnum {
        ELEMENTARY, COMPOSITE
    }
}
