package ru.dchertanov.splinecurves.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.dchertanov.splinecurves.util.graphics.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.points.Point;
import ru.dchertanov.splinecurves.util.graphics.CurveMode;

import java.io.IOException;
import java.util.Optional;

public class BezierCurveController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private PixelatedCanvas repeatingCanvas;
    @FXML
    private Button backButton;
    private boolean isMovingPoint = false;
    private int movingPointIndex = -1;
    private CurveMode mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_MATRIX);
    private CurveMode repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_CASTELJAU);

    public void initialize() {
        MainViewController.configureBackToMainButton(backButton);
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
            mainCurveMode.removePreviousCurve(PixelatedCanvas.getRGBFromColor(Color.WHITE), mainCanvas);
            repeatingCurveMode.removePreviousCurve(PixelatedCanvas.getRGBFromColor(Color.valueOf("#ccffcc")), repeatingCanvas);

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
            if (mainCurveMode.isClosed()) {
                mainCurveMode = CurveMode.getInstance(mainCurveMode.getCurveModeEnum());
                repeatingCurveMode = CurveMode.getInstance(repeatingCurveMode.getCurveModeEnum());
            }

            mainCurveMode.removePreviousCurve(PixelatedCanvas.getRGBFromColor(Color.WHITE), mainCanvas);
            repeatingCurveMode.removePreviousCurve(PixelatedCanvas.getRGBFromColor(Color.valueOf("#ccffcc")), repeatingCanvas);

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
                PixelatedCanvas.getRGBFromColor(Color.WHITE),
                mainCanvas);
        repeatingCurveMode.closeCurve(PixelatedCanvas.getRGBFromColor(Color.RED),
                PixelatedCanvas.getRGBFromColor(Color.BLUE),
                PixelatedCanvas.getRGBFromColor(Color.BLACK),
                PixelatedCanvas.getRGBFromColor(Color.valueOf("#ccffcc")),
                repeatingCanvas);
    }

    @FXML
    protected void setElementaryMode() {
        mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_MATRIX);
        repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.ELEMENTARY_CASTELJAU);
        onClearButtonClick();
    }

    @FXML
    protected void setCompositeMode() {
        mainCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_MATRIX);
        repeatingCurveMode = CurveMode.getInstance(CurveMode.CurveModeEnum.COMPOSITE_CASTELJAU);
        onClearButtonClick();
    }

    @FXML
    protected void onPlusMainCanvasClick() {
        MainViewController.zoom(mainCanvas, true);
    }

    @FXML
    protected void onMinusMainCanvasClick() {
        MainViewController.zoom(mainCanvas, false);
    }

    @FXML
    protected void onPlusRepeatingCanvasClick() {
        MainViewController.zoom(repeatingCanvas, true);
    }

    @FXML
    protected void onMinusRepeatingCanvasClick() {
        MainViewController.zoom(repeatingCanvas, false);
    }

    @FXML
    protected void backToMainClick() throws IOException {
        MainViewController.backToMainClick((Stage) mainCanvas.getScene().getWindow());
    }

    @FXML
    protected void onClearButtonClick() {
        mainCurveMode.clearPoints();
        repeatingCurveMode.clearPoints();
        mainCanvas.clearCanvas();
        repeatingCanvas.clearCanvas();
    }

    @FXML
    protected void aboutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе:");
        alert.setHeaderText(null);
        alert.setContentText("Зеленый - внутри, фиолетовый - снаружи, синий - нужно проверять.");
        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
                "Платформа: JavaFX.");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 300);
        alert.showAndWait();
    }
}
