package ru.dchertanov.drawdemoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
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