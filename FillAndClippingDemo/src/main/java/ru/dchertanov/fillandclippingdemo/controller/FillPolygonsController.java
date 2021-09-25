package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.dchertanov.fillandclippingdemo.algo.FillPolygons;
import ru.dchertanov.fillandclippingdemo.util.Edge;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FillPolygonsController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button backButton;

    private Point firstPoint;
    private Point lastPoint;
    private Point previousPoint;
    private List<Edge> edges = new ArrayList<>();
    private boolean isDrawing = false;

    public void initialize() {
        Image image = new Image("back_arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        backButton.setGraphic(imageView);
    }

    @FXML
    protected void onMainCanvasClick(MouseEvent mouseEvent) {
        if (!isDrawing)
            return;
        
        lastPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (firstPoint == null) {
            firstPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        } else {
            edges.add(new Edge(previousPoint, lastPoint));
            mainCanvas.drawLine(previousPoint, lastPoint);
        }
        previousPoint = lastPoint;
    }

    @FXML
    protected void fillPolygon() {
        FillPolygons.fillByHorizontalLines(mainCanvas, edges, PixelatedCanvas.getRGBFromColor(colorPicker.getValue()));
    }

    @FXML
    protected void startDrawing() {
        firstPoint = null;
        lastPoint = null;
        previousPoint = null;
        edges = new ArrayList<>();
        mainCanvas.clear();
        isDrawing = true;
    }

    @FXML
    protected void endDrawing() {
        if (!isDrawing)
            return;

        isDrawing = false;
        edges.add(new Edge(lastPoint, firstPoint));
        mainCanvas.drawLine(lastPoint, firstPoint);
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clear();
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
