package ru.dchertanov.splinecurves.util.graphics;

import javafx.scene.paint.Color;
import ru.dchertanov.splinecurves.algo.ElementaryCasteljauBezierCurve;
import ru.dchertanov.splinecurves.algo.ElementaryMatrixBSpline;
import ru.dchertanov.splinecurves.algo.ElementaryMatrixBezierCurve;
import ru.dchertanov.splinecurves.util.TernaryConsumer;
import ru.dchertanov.splinecurves.util.points.Point;

import java.util.List;
import java.util.Optional;

public final class CurveMode {
    private final MovablePointsHandler movablePointsHandler = new MovablePointsHandler();
    private final CurveModeEnum curveModeEnum;
    private TernaryConsumer<Integer, PixelatedCanvas, TernaryConsumer<List<Point>, Integer, PixelatedCanvas>> curveDrawer;
    private TernaryConsumer<List<Point>, Integer, PixelatedCanvas> curveDrawerAlgo;
    private boolean isClosed = false;

    public CurveMode(CurveModeEnum curveModeEnum) {
        this.curveModeEnum = curveModeEnum;
    }

    public void drawCurve(int pointColorRGB, int borderColorRGB, int curveColorRGB, PixelatedCanvas canvas) {
        drawPoints(pointColorRGB, canvas);
        movablePointsHandler.drawBorder(borderColorRGB, canvas);
        curveDrawer.apply(curveColorRGB, canvas, curveDrawerAlgo);
        drawPoints(pointColorRGB, canvas);
    }

    private void drawPoints(int pointColorRGB, PixelatedCanvas canvas) {
        movablePointsHandler.drawPoints(pointColorRGB, canvas);
        if (curveModeEnum.equals(CurveModeEnum.COMPOSITE_MATRIX) || curveModeEnum.equals(CurveModeEnum.COMPOSITE_CASTELJAU)) {
            movablePointsHandler.drawExtraPoints(PixelatedCanvas.getRGBFromColor(Color.GREEN), canvas, isClosed);
        }
    }

    public void closeCurve(int pointColorRGB, int borderColorRGB, int curveColorRGB, Color backgroundColor, PixelatedCanvas canvas) {
        isClosed = true;

        canvas.clearCanvas(backgroundColor);
        if (curveModeEnum.equals(CurveModeEnum.ELEMENTARY_MATRIX) || curveModeEnum.equals(CurveModeEnum.ELEMENTARY_CASTELJAU)) {
            movablePointsHandler.addPoint(movablePointsHandler.getPoint(0));
        } else if (curveModeEnum.equals(CurveModeEnum.COMPOSITE_MATRIX) || curveModeEnum.equals(CurveModeEnum.COMPOSITE_CASTELJAU)) {
            movablePointsHandler.closeCompositeCurve();
        } else {
            movablePointsHandler.closeCompositeBSpline();
        }
        drawCurve(pointColorRGB, borderColorRGB, curveColorRGB, canvas);
    }

    public void addPoint(Point point) {
        movablePointsHandler.addPoint(point);
    }

    public void replacePoint(int movingIndex, Point point) {
        movablePointsHandler.replacePoint(movingIndex, point);
    }

    public Optional<Integer> getNearestPoint(Point point) {
        return movablePointsHandler.getNearestPoint(point, isClosed, curveModeEnum);
    }

    public void clearPoints() {
        movablePointsHandler.clearPoints();
        isClosed = false;
    }

    public CurveModeEnum getCurveModeEnum() {
        return curveModeEnum;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static CurveMode getInstance(CurveModeEnum curveMode) {
        CurveMode instance = null;
        switch (curveMode) {
            case ELEMENTARY_MATRIX:
                instance = new CurveMode(CurveModeEnum.ELEMENTARY_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawElementaryBezierCurve;
                instance.curveDrawerAlgo = ElementaryMatrixBezierCurve::drawBezierCurve;
                break;
            case COMPOSITE_MATRIX:
                instance = new CurveMode(CurveModeEnum.COMPOSITE_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawCompositeBezierCurve;
                instance.curveDrawerAlgo = ElementaryMatrixBezierCurve::drawBezierCurve;
                break;
            case ELEMENTARY_CASTELJAU:
                instance = new CurveMode(CurveModeEnum.ELEMENTARY_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawElementaryBezierCurve;
                instance.curveDrawerAlgo = ElementaryCasteljauBezierCurve::drawBezierCurve;
                break;
            case COMPOSITE_CASTELJAU:
                instance = new CurveMode(CurveModeEnum.COMPOSITE_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawCompositeBezierCurve;
                instance.curveDrawerAlgo = ElementaryCasteljauBezierCurve::drawBezierCurve;
                break;
            case COMPOSITE_B_SPLINE_MATRIX:
                instance = new CurveMode(CurveModeEnum.COMPOSITE_B_SPLINE_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawCompositeBSpline;
                instance.curveDrawerAlgo = ElementaryMatrixBSpline::drawBezierCurve;
                break;
            case COMPOSITE_B_SPLINE_BURA:
                instance = new CurveMode(CurveModeEnum.COMPOSITE_B_SPLINE_MATRIX);
                instance.curveDrawer = instance.movablePointsHandler::drawCompositeBSpline;
                instance.curveDrawerAlgo = (a, b, c) -> {
                }; // tmp
                break;
        }

        return instance;
    }

    public enum CurveModeEnum {
        ELEMENTARY_MATRIX, COMPOSITE_MATRIX, ELEMENTARY_CASTELJAU, COMPOSITE_CASTELJAU, COMPOSITE_B_SPLINE_MATRIX, COMPOSITE_B_SPLINE_BURA
    }
}
