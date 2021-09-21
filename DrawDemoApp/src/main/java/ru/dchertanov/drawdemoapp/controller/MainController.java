package ru.dchertanov.drawdemoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import ru.dchertanov.drawdemoapp.algo.Figure;

public class MainController {
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas repeatingCanvas;

    private Figure currentFigure = Figure.getInstance("line");

    @FXML
    protected void onMainCanvasPressed(MouseEvent mouseEvent) {
        currentFigure.setFigureStartPoint((int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    @FXML
    protected void onMainCanvasDragged(MouseEvent mouseEvent) {
        currentFigure.drawCustomFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(), mainCanvas.getGraphicsContext2D());
    }

    @FXML
    protected void onMainCanvasMouseExited() {
        currentFigure.fillRepeatingCanvas(repeatingCanvas.getGraphicsContext2D());
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        repeatingCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    private void zoom(Canvas canvas, boolean increase) {
        if (increase) {
            canvas.getTransforms().add(new Scale(1.5, 1.5));
        } else {
            canvas.getTransforms().add(new Scale(1 / 1.5, 1 / 1.5));
        }
    }

    @FXML
    protected void onLineClick() {
        currentFigure = Figure.getInstance("line");
    }

    @FXML
    protected void onCircleClick() {
        currentFigure = Figure.getInstance("circle");
    }

    @FXML
    protected void onEllipseClick() {
        currentFigure = Figure.getInstance("ellipse");
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
}