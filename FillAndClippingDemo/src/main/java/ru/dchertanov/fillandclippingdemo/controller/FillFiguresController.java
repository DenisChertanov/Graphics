package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.dchertanov.fillandclippingdemo.algo.FillFigures;
import ru.dchertanov.fillandclippingdemo.figures.Figure;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.io.IOException;

public class FillFiguresController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button backButton;

    private Figure currentFigure = Figure.getInstance("line");
    private boolean isFillMode = false;

    public void initialize() {
        MainViewController.configureBackToMainButton(backButton);
    }

    @FXML
    protected void onFillRegionClick(MouseEvent mouseEvent) {
        if (!isFillMode)
            return;

        FillFigures.fillByRowsWithPoint(mainCanvas, PixelatedCanvas.getRGBFromColor(colorPicker.getValue()),
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

        currentFigure.drawFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                false, PixelatedCanvas.getRGBFromColor(Color.BLACK), mainCanvas);
    }

    @FXML
    protected void onMainCanvasReleased(MouseEvent mouseEvent) {
        if (currentFigure == null)
            return;

        currentFigure.drawFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                true, PixelatedCanvas.getRGBFromColor(Color.BLACK), mainCanvas);
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clearCanvas();
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

    @FXML
    protected void onPlusMainCanvasClick() {
        MainViewController.zoom(mainCanvas, true);
    }

    @FXML
    protected void onMinusMainCanvasClick() {
        MainViewController.zoom(mainCanvas, false);
    }

    @FXML
    protected void backToMainClick() throws IOException {
        MainViewController.backToMainClick((Stage) mainCanvas.getScene().getWindow());
    }

    @FXML
    protected void aboutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе:");
        alert.setHeaderText(null);
//        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
//                "Платформа: JavaFX.\n" +
//                "Задание: Реализовать алгоритмы Брезенхема для построения отрезка, окружности, эллипса. " +
//                "Для сравнения показать аналогичные фигуры, нарисованные встроенными библиотеками.\n" +
//                "Использование: В левой части экрана необходимо выбрать фигуру для построения. " +
//                "Рисование осуществляется на белом полотне (рисование с помощью зажатия кнопки на мышке). " +
//                "На белом полотне рисуется фигура, построенная алгоритмами Брезенхема. " +
//                "На правом зеленом полотне рисуется аналогичная фигура, построенная встроенными библиотеками.");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 250);
        alert.showAndWait();
    }
}