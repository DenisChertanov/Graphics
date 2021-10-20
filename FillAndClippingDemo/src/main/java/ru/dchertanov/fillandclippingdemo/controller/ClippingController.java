package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.dchertanov.fillandclippingdemo.algo.ClippingLines;
import ru.dchertanov.fillandclippingdemo.figures.Figure;
import ru.dchertanov.fillandclippingdemo.figures.Line;
import ru.dchertanov.fillandclippingdemo.figures.Rectangle;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClippingController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private Button backButton;

    private Figure currentFigure = Figure.getInstance("line");
    private List<Line> lines = new ArrayList<>();
    private Rectangle rectangle;

    public void initialize() {
        MainViewController.configureBackToMainButton(backButton);
    }

    @FXML
    protected void onLineClick() {
        currentFigure = Figure.getInstance("line");
    }

    @FXML
    protected void onRectangleClick() {
        currentFigure = Figure.getInstance("rectangle");
    }

    @FXML
    protected void onShowLineTypeClick() {
        ClippingLines.showLineTypes(lines, rectangle, mainCanvas);
    }

    @FXML
    protected void onClippingClick() {
        ClippingLines.clipping(lines, rectangle, mainCanvas);
    }

    @FXML
    protected void onMainCanvasPressed(MouseEvent mouseEvent) {
        if (currentFigure instanceof Rectangle && rectangle != null) {
            return;
        }
        currentFigure.setFigureStartPoint((int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    @FXML
    protected void onMainCanvasDragged(MouseEvent mouseEvent) {
        if (currentFigure instanceof Rectangle && rectangle != null) {
            return;
        }
        int rgb = currentFigure instanceof Line ? PixelatedCanvas.getRGBFromColor(Color.BLACK) : PixelatedCanvas.getRGBFromColor(Color.RED);
        currentFigure.drawFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                false, rgb, mainCanvas);
    }

    /**
     * Method draws final version of figure
     */
    @FXML
    protected void onMainCanvasReleased(MouseEvent mouseEvent) {
        if (currentFigure instanceof Rectangle && rectangle != null) {
            return;
        }

        int rgb = currentFigure instanceof Line ? PixelatedCanvas.getRGBFromColor(Color.BLACK) : PixelatedCanvas.getRGBFromColor(Color.RED);
        currentFigure.drawFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                true, rgb, mainCanvas);
        if (currentFigure instanceof Rectangle) {
            rectangle = (Rectangle) currentFigure;
            currentFigure = Figure.getInstance("rectangle");
        } else {
            lines.add((Line) currentFigure);
            currentFigure = Figure.getInstance("line");
        }
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clearCanvas();
        lines = new ArrayList<>();
        rectangle = null;
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
        alert.setContentText("Зеленый - внутри, фиолетовый - снаружи, синий - нужно проверять.");
        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
                "Платформа: JavaFX.\n" +
                "Задание: Реализовать алгоритм Сазерленда-Коэна для отсечения отрезков. \n" +
                "Использование: \n" +
                "1) В левой части экрана выбрать построение отрезков, нарисовать нужные зажатием мышки\n" +
                "2) В левой части экрана выбрать построение прямоугольника, нарисовать нужный зажатием мышки\n" +
                "3) В левой части экрана нажать \"Подсветить видимость\". Зеленый - точно внутри, Фиолетовый - точно снаружи, Синий - нужно смотреть детальней.\n" +
                "4) В левой части экрана нажать \"Отсечь\" для отсечения отрезков");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 300);
        alert.showAndWait();
    }
}