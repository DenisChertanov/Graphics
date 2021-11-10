package ru.dchertanov.splinecurves.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import ru.dchertanov.splinecurves.util.graphics.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.points.Point;
import ru.dchertanov.splinecurves.util.graphics.CurveMode;

import java.util.Optional;

public class BezierCurveController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private PixelatedCanvas repeatingCanvas;
    private boolean isMovingPoint = false;
    private int movingPointIndex = -1;
    private CurveMode mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_MATRIX);
    private CurveMode repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_CASTELJAU);

    public void initialize() {
        var gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        onMinusMainCanvasClick();
        onMinusRepeatingCanvasClick();
    }

    @FXML
    protected void onMainCanvasPressed(MouseEvent mouseEvent) {
        Optional<Integer> movingPoint = mainCurveMode.getNearestPoint(new Point(mouseEvent));
        if (movingPoint.isPresent()) {
            // handle movable point
            isMovingPoint = true;
            movingPointIndex = movingPoint.get();
        }
    }

    @FXML
    protected void onMainCanvasDragged(MouseEvent mouseEvent) {
        if (isMovingPoint) {
            mainCanvas.clearCanvas(Color.WHITE);
            repeatingCanvas.clearCanvas(Color.valueOf("#ccffcc"));

            mainCurveMode.replacePoint(movingPointIndex, new Point(mouseEvent));
            repeatingCurveMode.replacePoint(movingPointIndex, new Point(mouseEvent));

            mainCurveMode.drawCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                    PixelatedCanvas.getRGBFromColor(Color.BLUE),
                    PixelatedCanvas.getRGBFromColor(Color.BLACK),
                    mainCanvas);
            repeatingCurveMode.drawCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                    PixelatedCanvas.getRGBFromColor(Color.BLUE),
                    PixelatedCanvas.getRGBFromColor(Color.BLACK),
                    repeatingCanvas);
        }
    }

    @FXML
    protected void onMainCanvasReleased(MouseEvent mouseEvent) {
        if (isMovingPoint) {
            isMovingPoint = false;
        } else {
            // Add new point
            mainCanvas.clearCanvas(Color.WHITE);
            repeatingCanvas.clearCanvas(Color.valueOf("#ccffcc"));

            mainCurveMode.addPoint(new Point(mouseEvent));
            repeatingCurveMode.addPoint(new Point(mouseEvent));
            mainCurveMode.drawCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                    PixelatedCanvas.getRGBFromColor(Color.BLUE),
                    PixelatedCanvas.getRGBFromColor(Color.BLACK),
                    mainCanvas);
            repeatingCurveMode.drawCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                    PixelatedCanvas.getRGBFromColor(Color.BLUE),
                    PixelatedCanvas.getRGBFromColor(Color.BLACK),
                    repeatingCanvas);
        }
    }

    @FXML
    protected void onCloseCurveClick() {
        mainCurveMode.closeCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                PixelatedCanvas.getRGBFromColor(Color.BLUE),
                PixelatedCanvas.getRGBFromColor(Color.BLACK),
                Color.WHITE,
                mainCanvas);
        repeatingCurveMode.closeCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                PixelatedCanvas.getRGBFromColor(Color.BLUE),
                PixelatedCanvas.getRGBFromColor(Color.BLACK),
                Color.valueOf("#ccffcc"),
                repeatingCanvas);

        mainCurveMode = CurveMode.getInstance(mainCurveMode.getCurveModeEnum());
        repeatingCurveMode = CurveMode.getInstance(repeatingCurveMode.getCurveModeEnum());
    }

    @FXML
    protected void setElementaryModeBezier() {
        mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_MATRIX);
        repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_CASTELJAU);
        repeatingCanvas.setVisible(true); // remove when add Boor
        onClearButtonClick();
    }

    @FXML
    protected void setCompositeModeBezier() {
        mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_MATRIX);
        repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_CASTELJAU);
        repeatingCanvas.setVisible(true); // remove when add Boor
        onClearButtonClick();
    }

    @FXML
    protected void setCompositeModeBSpline() {
        mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_B_SPLINE_MATRIX);
        repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_B_SPLINE_BURA);
        repeatingCanvas.setVisible(false); // remove when add Boor
        onClearButtonClick();
    }

    @FXML
    protected void onPlusMainCanvasClick() {
        zoom(mainCanvas, true);
    }

    @FXML
    protected void onMinusMainCanvasClick() {
        zoom(mainCanvas, false);
    }

    @FXML
    protected void onPlusRepeatingCanvasClick() {
        zoom(repeatingCanvas, true);
    }

    @FXML
    protected void onMinusRepeatingCanvasClick() {
        zoom(repeatingCanvas, false);
    }

    @FXML
    protected void onClearButtonClick() {
        mainCurveMode.clearPoints();
        repeatingCurveMode.clearPoints();
        mainCanvas.clearCanvas();
        repeatingCanvas.clearCanvas();

        mainCanvas.clearCanvas(Color.WHITE);
        repeatingCanvas.clearCanvas(Color.valueOf("#ccffcc"));
    }

    public static void zoom(Canvas canvas, boolean increase) {
        if (increase) {
            canvas.getTransforms().add(new Scale(1.5, 1.5));
        } else {
            canvas.getTransforms().add(new Scale(1 / 1.5, 1 / 1.5));
        }
    }

    @FXML
    protected void aboutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе:");
        alert.setHeaderText(null);
        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
                "Платформа: JavaFX.\n" +
                "Задание: Реализовать 2 алгоритма построения кривых Безье (матричный, алгоритм Кастельжо).\n" +
                "Продемонстрировать построение элементарной произвольного порядка, составной из элементарных 3го порядка.\n" +
                "Реализовать замыкание кривых.\n" +
                "Реализовать построение B-сплайна мртичным алгоритмом.\n" +
                "Использование: В левой части экрана необходимо выбрать режим построения кривых.\n" +
                "Рисование осуществляется на белом полотне (с помощью нажатий кнопки мышки ставятся опорные точки кривой,\n" +
                "С помощью зажатия мышки и перетаскивания происходит изменение положение опорной точки)\n" +
                "На белом полотне рисуется кривая, построенная с помощью матричного метода. (для Безье и B-сплайнов)\n" +
                "На зеленом полотне рисуется кривая, построенная с помощью алгоритма Кастельжо. (только для Безье)\n" +
                "Для замыкания необходимо нажать кнопку \"Замкнуть\".");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 400);
        alert.showAndWait();
    }
}
