package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
        MainViewController.configureBackToMainButton(backButton);
    }

    /**
     * Method adds new point in polygon, adds new edge
     */
    @FXML
    protected void onNewPolygonPointClick(MouseEvent mouseEvent) {
        if (!isDrawing)
            return;

        lastPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (firstPoint == null) {
            firstPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        } else {
            if (!edges.isEmpty()) {
                edges.get(edges.size() - 1).setNextEdgePoint(lastPoint, true);
            }

            edges.add(new Edge(previousPoint, lastPoint));
            mainCanvas.drawLine(previousPoint, lastPoint, PixelatedCanvas.getRGBFromColor(Color.BLACK));
        }
        previousPoint = lastPoint;
    }

    @FXML
    protected void fillPolygon() {
        if (isDrawing || edges.size() < 3)
            return;

        FillPolygons.fillByHorizontalLines(mainCanvas, edges, PixelatedCanvas.getRGBFromColor(colorPicker.getValue()));
        drawPolygonBorder();
    }

    private void drawPolygonBorder() {
        for (Edge edge : edges) {
            Edge scaledEdge = Edge.getScaledEdge(edge);
            mainCanvas.drawLine(scaledEdge.getStartPoint(), scaledEdge.getEndPoint(), PixelatedCanvas.getRGBFromColor(Color.BLACK));
        }
    }

    @FXML
    protected void startDrawing() {
        firstPoint = null;
        lastPoint = null;
        previousPoint = null;
        edges = new ArrayList<>();
        mainCanvas.clearCanvas();
        isDrawing = true;
    }

    @FXML
    protected void endDrawing() {
        if (!isDrawing)
            return;

        isDrawing = false;
        edges.get(edges.size() - 1).setNextEdgePoint(firstPoint, true);
        edges.add(new Edge(lastPoint, firstPoint));
        edges.get(edges.size() - 1).setNextEdgePoint(edges.get(0).getNextEdgePoint(), false);
        mainCanvas.drawLine(lastPoint, firstPoint, PixelatedCanvas.getRGBFromColor(Color.BLACK));
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clearCanvas();
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
