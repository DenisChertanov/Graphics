package ru.dchertanov.splinecurves.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.dchertanov.splinecurves.figures.Circle;
import ru.dchertanov.splinecurves.figures.Figure;
import ru.dchertanov.splinecurves.filling.FillFigures;
import ru.dchertanov.splinecurves.util.MovablePointsHandler;
import ru.dchertanov.splinecurves.util.PixelatedCanvas;
import ru.dchertanov.splinecurves.util.Point;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

public class BezierCurveController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private PixelatedCanvas repeatingCanvas;
    @FXML
    private Button backButton;
    private MovablePointsHandler movablePointsHandler = new MovablePointsHandler();
    private boolean isMovingPoint = false;
    private int movingPointIndex = -1;

    public void initialize() {
        MainViewController.configureBackToMainButton(backButton);
    }

    @FXML
    protected void onMainCanvasPressed(MouseEvent mouseEvent) {
        Optional<Integer> movingPoint = movablePointsHandler.getNearestPoint(new Point(mouseEvent));
        if (movingPoint.isPresent()) {
            // handle movable point
            isMovingPoint = true;
            movingPointIndex = movingPoint.get();
        }
    }

    @FXML
    protected void onMainCanvasDragged(MouseEvent mouseEvent) {
        if (isMovingPoint) {
            removePreviousFigure();

            movablePointsHandler.replacePoint(movingPointIndex, new Point(mouseEvent));

            movablePointsHandler.drawPoints(PixelatedCanvas.getRGBFromColor(Color.RED), mainCanvas);
            movablePointsHandler.drawBorder(PixelatedCanvas.getRGBFromColor(Color.BLUE), mainCanvas);
            movablePointsHandler.drawPoints(PixelatedCanvas.getRGBFromColor(Color.RED), mainCanvas);
        }
    }

    @FXML
    protected void onMainCanvasReleased(MouseEvent mouseEvent) {
        if (isMovingPoint) {
            isMovingPoint = false;
        } else {
            // Add new point
            removePreviousFigure();

            movablePointsHandler.addPoint(new Point(mouseEvent));

            movablePointsHandler.drawPoints(PixelatedCanvas.getRGBFromColor(Color.RED), mainCanvas);
            movablePointsHandler.drawBorder(PixelatedCanvas.getRGBFromColor(Color.BLUE), mainCanvas);
            movablePointsHandler.drawPoints(PixelatedCanvas.getRGBFromColor(Color.RED), mainCanvas);
        }
    }

    private void removePreviousFigure() {
        movablePointsHandler.drawPoints(PixelatedCanvas.getRGBFromColor(Color.WHITE), mainCanvas);
        movablePointsHandler.drawBorder(PixelatedCanvas.getRGBFromColor(Color.WHITE), mainCanvas);
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
        movablePointsHandler.clearPoints();
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
