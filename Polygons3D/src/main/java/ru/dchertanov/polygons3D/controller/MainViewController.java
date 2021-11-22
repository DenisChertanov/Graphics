package ru.dchertanov.polygons3D.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import ru.dchertanov.polygons3D.drawer.CanvasDrawer;
import ru.dchertanov.polygons3D.figure.Cube;
import ru.dchertanov.polygons3D.figure.Tetrahedron;

public class MainViewController {
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Slider xRotation;
    @FXML
    private Slider yRotation;
    @FXML
    private Slider zRotation;
    @FXML
    private Slider xShift;
    @FXML
    private Slider yShift;
    @FXML
    private Slider zShift;
    @FXML
    private Slider xScaling;
    @FXML
    private Slider yScaling;
    @FXML
    private Slider zScaling;
    @FXML
    private Slider xyzScaling;
    @FXML
    private RadioButton shiftRotate;
    @FXML
    private RadioButton rotateShift;

    private final CanvasDrawer canvasDrawer = new CanvasDrawer();

    public void initialize() {
        canvasDrawer.drawFigure(mainCanvas, Color.BLACK);

        // rotation sliders
        xRotation.valueProperty().addListener(val -> {
            canvasDrawer.figure.rotation.setxAngle(xRotation.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        yRotation.valueProperty().addListener(val -> {
            canvasDrawer.figure.rotation.setyAngle(yRotation.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        zRotation.valueProperty().addListener(val -> {
            canvasDrawer.figure.rotation.setzAngle(zRotation.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });

        // shift sliders
        xShift.valueProperty().addListener(val -> {
            canvasDrawer.figure.shift.setxShift(xShift.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        yShift.valueProperty().addListener(val -> {
            canvasDrawer.figure.shift.setyShift(yShift.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        zShift.valueProperty().addListener(val -> {
            canvasDrawer.figure.shift.setzShift(zShift.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });

        // scaling sliders
        xScaling.valueProperty().addListener(val -> {
            canvasDrawer.figure.scaling.setxScaling(xScaling.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        yScaling.valueProperty().addListener(val -> {
            canvasDrawer.figure.scaling.setyScaling(yScaling.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        zScaling.valueProperty().addListener(val -> {
            canvasDrawer.figure.scaling.setzScaling(zScaling.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        xyzScaling.valueProperty().addListener(val -> {
            canvasDrawer.figure.scaling.setxScaling(xyzScaling.getValue());
            canvasDrawer.figure.scaling.setyScaling(xyzScaling.getValue());
            canvasDrawer.figure.scaling.setzScaling(xyzScaling.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);

            xScaling.setValue(xyzScaling.getValue());
            yScaling.setValue(xyzScaling.getValue());
            zScaling.setValue(xyzScaling.getValue());
        });

        // order radio buttons
        shiftRotate.setOnAction(actionEvent -> {
            canvasDrawer.figure.shiftFirst = true;
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        rotateShift.setOnAction(actionEvent -> {
            canvasDrawer.figure.shiftFirst = false;
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
    }

    @FXML
    protected void dump() {
        xRotation.setValue(0);
        yRotation.setValue(0);
        zRotation.setValue(0);

        xShift.setValue(0);
        yShift.setValue(0);
        zShift.setValue(0);

        xScaling.setValue(0);
        yScaling.setValue(0);
        zScaling.setValue(0);
        xyzScaling.setValue(0);

        canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
    }

    @FXML
    protected void setCube() {
        dump();
        clear();
        canvasDrawer.figure = new Cube();
        canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
    }

    @FXML
    protected void setTetrahedron() {
        dump();
        clear();
        canvasDrawer.figure = new Tetrahedron();
        canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
    }

    private void clear() {
        var graphics = mainCanvas.getGraphicsContext2D();
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
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
