package ru.dchertanov.drawdemoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import ru.dchertanov.drawdemoapp.algo.FloodFill;
import ru.dchertanov.drawdemoapp.figures.Figure;
import ru.dchertanov.drawdemoapp.util.PixelatedCanvas;
import ru.dchertanov.drawdemoapp.util.Point;

public class MainController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private PixelatedCanvas repeatingCanvas;

    private Figure currentFigure = Figure.getInstance("line");
    private boolean isFillMode = false;

    @FXML
    protected void onMainCanvasClick(MouseEvent mouseEvent) {
        if (!isFillMode)
            return;

        FloodFill.fillByRowsWithPoint(mainCanvas, PixelatedCanvas.getRGBFromColor(Color.PINK),
                new Point((int) mouseEvent.getX() / 2, (int) mouseEvent.getY() / 2));
    }

    @FXML
    protected void onMainCanvasPressed(MouseEvent mouseEvent) {
        if (currentFigure == null)
            return;

        currentFigure.setFigureStartPoint((int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    @FXML
    protected void onMainCanvasDragged(MouseEvent mouseEvent) {
        if (currentFigure == null)
            return;

        currentFigure.drawCustomFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(), mainCanvas);
    }

    @FXML
    protected void onMainCanvasMouseExited() {
        if (currentFigure == null)
            return;

        currentFigure.fillRepeatingCanvas(repeatingCanvas.getGraphicsContext2D());
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clear();
        repeatingCanvas.clear();
    }

    @FXML
    protected void onLineClick() {
        currentFigure = Figure.getInstance("line");
        isFillMode = false;
    }

    @FXML
    protected void onCircleClick() {
        currentFigure = Figure.getInstance("circle");
        isFillMode = false;
    }

    @FXML
    protected void onEllipseClick() {
        currentFigure = Figure.getInstance("ellipse");
        isFillMode = false;
    }

    @FXML
    protected void onFillClick() {
        isFillMode = true;
        currentFigure = null;
    }

    private void zoom(Canvas canvas, boolean increase) {
        if (increase) {
            canvas.getTransforms().add(new Scale(1.5, 1.5));
        } else {
            canvas.getTransforms().add(new Scale(1 / 1.5, 1 / 1.5));
        }
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
    protected void onChooseColorClick() {
        // choose fill color
    }

    @FXML
    protected void aboutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе:");
        alert.setHeaderText(null);
        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
                "Платформа: JavaFX.\n" +
                "Задание: Реализовать алгоритмы Брезенхема для построения отрезка, окружности, эллипса. " +
                "Для сравнения показать аналогичные фигуры, нарисованные встроенными библиотеками.\n" +
                "Использование: В левой части экрана необходимо выбрать фигуру для построения. " +
                "Рисование осуществляется на белом полотне (рисование с помощью зажатия кнопки на мышке). " +
                "На белом полотне рисуется фигура, построенная алгоритмами Брезенхема. " +
                "На правом зеленом полотне рисуется аналогичная фигура, построенная встроенными библиотеками.");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 250);
        alert.showAndWait();
    }
}