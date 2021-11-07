package ru.dchertanov.splinecurves.util;

import java.util.Optional;

public final class CurveMode {
    private final MovablePointsHandler movablePointsHandler = new MovablePointsHandler();
    private BinaryConsumer<Integer, PixelatedCanvas> curveDrawer;

    public void removePreviousCurve(int colorRGB, PixelatedCanvas canvas) {
        movablePointsHandler.drawPoints(colorRGB, canvas);
        movablePointsHandler.drawBorder(colorRGB, canvas);
        curveDrawer.apply(colorRGB, canvas);
    }

    public void drawCurve(int pointColorRGB, int borderColorRGB, int curveColorRGB, PixelatedCanvas canvas) {
        movablePointsHandler.drawPoints(pointColorRGB, canvas);
        movablePointsHandler.drawBorder(borderColorRGB, canvas);
        curveDrawer.apply(curveColorRGB, canvas);
        movablePointsHandler.drawPoints(pointColorRGB, canvas);
    }

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
                instance = new CurveMode();
                instance.curveDrawer = instance.movablePointsHandler::drawElementaryBezierCurve;
                break;
            case COMPOSITE:
                instance = new CurveMode();
                instance.curveDrawer = instance.movablePointsHandler::drawCompositeBezierCurve;
                break;
        }

        return instance;
    }

    public enum CurveModeEnum {
        ELEMENTARY, COMPOSITE
    }
}
