package ru.dchertanov.splinecurves.util.curve.mode;

import ru.dchertanov.splinecurves.util.PixelatedCanvas;

public class CompositeCurveMode extends CurveMode {
    @Override
    public void removePreviousCurve(int colorRGB, PixelatedCanvas canvas) {
        movablePointsHandler.drawPoints(colorRGB, canvas);
        movablePointsHandler.drawBorder(colorRGB, canvas);
        movablePointsHandler.drawCompositeBezierCurve(colorRGB, canvas);
    }

    @Override
    public void drawCurve(int pointColorRGB, int borderColorRGB, int curveColorRGB, PixelatedCanvas canvas) {
        movablePointsHandler.drawPoints(pointColorRGB, canvas);
        movablePointsHandler.drawBorder(borderColorRGB, canvas);
        movablePointsHandler.drawCompositeBezierCurve(curveColorRGB, canvas);
        movablePointsHandler.drawPoints(pointColorRGB, canvas);
    }
}
