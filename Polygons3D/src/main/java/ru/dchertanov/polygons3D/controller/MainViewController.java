package ru.dchertanov.polygons3D.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
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
    @FXML
    private Slider cameraSlider;
    @FXML
    private RadioButton parallelProjection;
    @FXML
    private RadioButton singlePointProjection;

    private final CanvasDrawer canvasDrawer = new CanvasDrawer();

    public void initialize() {
        drawCoordinateArrows(mainCanvas, 300, 300);
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

        // camera slider
        cameraSlider.valueProperty().addListener(val -> {
            canvasDrawer.figure.singlePointProjection.setFocusDistance(cameraSlider.getValue());
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        // projection radio buttons
        parallelProjection.setOnAction(actionEvent -> {
            canvasDrawer.figure.parallelProjection = true;
            clear();
            canvasDrawer.drawFigure(mainCanvas, Color.BLACK);
        });
        singlePointProjection.setOnAction(actionEvent -> {
            canvasDrawer.figure.parallelProjection = false;
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

        xScaling.setValue(1);
        yScaling.setValue(1);
        zScaling.setValue(1);
        xyzScaling.setValue(1);

        cameraSlider.setValue(500);

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
        drawCoordinateArrows(mainCanvas, 300, 300);
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

    private void drawArrow(Canvas canvas, double x0, double y0, double x1, double y1) {
        var g2 = canvas.getGraphicsContext2D();
        g2.setStroke(Color.BLACK);
        g2.setFill(Color.BLACK);

        g2.strokeLine(x0, y0, x1, y1);

        double angle = Math.toDegrees(Math.atan2(y1 - y0, x1 - x0)) + 90;
        var rotation = Transform.rotate(angle, x1, y1);

        double side = 10;
        double cos30 = 0.87;
        double[] points = new double[]{x1 - side / 2, y1, x1 + side / 2, y1, x1, y1 - cos30 * side};
        double[] rotated = new double[6];

        rotation.transform2DPoints(points, 0, rotated, 0, 3);

        g2.fillPolygon(new double[]{rotated[0], rotated[2], rotated[4]},
                new double[]{rotated[1], rotated[3], rotated[5]}, 3);
    }

    private void drawCoordinateArrows(Canvas canvas, double xArrowSize, double yArrowSize) {
        var g2 = canvas.getGraphicsContext2D();
        g2.setStroke(Color.BLACK);
        double x0 = canvas.getWidth() / 2;
        double y0 = canvas.getHeight() / 2;

        drawArrow(canvas, x0, y0, x0 + xArrowSize, y0);
        drawArrow(canvas, x0, y0, x0, y0 - yArrowSize);

        g2.strokeText("x", x0 + xArrowSize + 20, y0);
        g2.strokeText("y", x0, y0 - yArrowSize - 20);

        g2.setFill(Color.WHITE);
//        g2.fillOval(x0 - 6, y0 - 6, 12, 12);
//        g2.strokeOval(x0 - 6, y0 - 6, 12, 12);
//        g2.setFill(Color.BLACK);
//        g2.fillOval(x0 - 2, y0 - 2, 4, 4);
    }
}
