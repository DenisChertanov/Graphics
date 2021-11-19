package ru.dchertanov.polygons3D.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.transform.Scale;

public class MainViewController {
    @FXML
    private Canvas mainCanvas;

    @FXML
    protected void onPlusMainCanvasClick() {
        zoom(mainCanvas, true);
    }

    @FXML
    protected void onMinusMainCanvasClick() {
        zoom(mainCanvas, false);
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
//        alert.setContentText("Зеленый - внутри, фиолетовый - снаружи, синий - нужно проверять.");
//        alert.setContentText("Выполнил: Чертанов Денис БПИ185.\n" +
//                "Платформа: JavaFX.\n" +
//                "Задание: Реализовать алгоритм Сазерленда-Коэна для отсечения отрезков. \n" +
//                "Использование: \n" +
//                "1) В левой части экрана выбрать построение отрезков, нарисовать нужные зажатием мышки\n" +
//                "2) В левой части экрана выбрать построение прямоугольника, нарисовать нужный зажатием мышки\n" +
//                "3) В левой части экрана нажать \"Подсветить видимость\". Зеленый - точно внутри, Фиолетовый - точно снаружи, Синий - нужно смотреть детальней.\n" +
//                "4) В левой части экрана нажать \"Отсечь\" для отсечения отрезков");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 300);
        alert.showAndWait();
    }
}
