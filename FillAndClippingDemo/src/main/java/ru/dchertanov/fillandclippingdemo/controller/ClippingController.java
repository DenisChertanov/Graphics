package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.dchertanov.fillandclippingdemo.figures.Figure;
import ru.dchertanov.fillandclippingdemo.figures.Line;
import ru.dchertanov.fillandclippingdemo.figures.Rectangle;
import ru.dchertanov.fillandclippingdemo.util.PixelatedCanvas;
import ru.dchertanov.fillandclippingdemo.util.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClippingController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private Button backButton;

    private Figure currentFigure = Figure.getInstance("line");
    private List<Figure> lines = new ArrayList<>();
    private Figure rectangle;

    public void initialize() {
        Image image = new Image("back_arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        backButton.setGraphic(imageView);
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
        for (Figure line : lines) {
            int startOutCode = line.getStartPoint().getOutCode((Rectangle) rectangle);
            int endOutCode = line.getEndPoint().getOutCode((Rectangle) rectangle);

            int rgb = 0;
            if (startOutCode == 0 && endOutCode == 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.GREEN);
            } else if ((startOutCode & endOutCode) != 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.PURPLE);
            } else {
                rgb = PixelatedCanvas.getRGBFromColor(Color.BLUE);
            }
            line.drawFigure(rgb, mainCanvas);
        }
    }

    @FXML
    protected void onClippingClick() {
        for (Figure line : lines) {
            int startOutCode = line.getStartPoint().getOutCode((Rectangle) rectangle);
            int endOutCode = line.getEndPoint().getOutCode((Rectangle) rectangle);

            int rgb = 0;
            if (startOutCode == 0 && endOutCode == 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.GREEN);
                line.drawFigure(rgb, mainCanvas);
            } else if ((startOutCode & endOutCode) != 0) {
                rgb = PixelatedCanvas.getRGBFromColor(Color.WHITE);
                line.drawFigure(rgb, mainCanvas);
            } else {
                if (startOutCode == 0 || line.getStartPoint().getX() > line.getEndPoint().getX()) {
                    line.swapPoints();
                    int tmp = startOutCode;
                    startOutCode = endOutCode;
                    endOutCode = tmp;
                }

                Point startIntersectionPoint = getIntersectionPoint(line.getStartPoint(), line.getEndPoint());
                if (startIntersectionPoint == null) {
                    line.drawFigure(PixelatedCanvas.getRGBFromColor(Color.WHITE), mainCanvas);
                } else {
                    mainCanvas.drawLine(line.getStartPoint().getScaledPoint(),
                            startIntersectionPoint.getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.WHITE));
                    if (endOutCode == 0) {
                        mainCanvas.drawLine(startIntersectionPoint.getScaledPoint(),
                                line.getEndPoint().getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.GREEN));
                    } else {
                        Point endIntersectionPoint = getIntersectionPoint(startIntersectionPoint, line.getEndPoint());
                        mainCanvas.drawLine(endIntersectionPoint.getScaledPoint(),
                                line.getEndPoint().getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.WHITE));
                        mainCanvas.drawLine(startIntersectionPoint.getScaledPoint(),
                                endIntersectionPoint.getScaledPoint(), PixelatedCanvas.getRGBFromColor(Color.GREEN));
                    }
                }
            }
        }

        rectangle.drawFigure(PixelatedCanvas.getRGBFromColor(Color.RED), mainCanvas);
    }

    private Point getIntersectionPoint(Point startPoint, Point endPoint) {
        // с левой стороной
        if (startPoint.getX() != endPoint.getX()) {
            double m = (endPoint.getY() - startPoint.getY())
                    / (double) (endPoint.getX() - startPoint.getX());
            int y = (int) Math.round(m *
                    (Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) - startPoint.getX()) +
                    startPoint.getY());
            if (y >= Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) &&
                    y <= Math.max(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) &&
                    !startPoint.equals(new Point(Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()), y))) {
                return new Point(Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()), y);
            }
        }

        // с верхней стороной
        if (startPoint.getY() != endPoint.getY()) {
            double m = (endPoint.getY() - startPoint.getY())
                    / (double) (endPoint.getX() - startPoint.getX());
            int x = (int) Math.round(m *
                    (1.0 / m) *
                    (Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) - startPoint.getY()));
            if (x >= Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) &&
                    x <= Math.max(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) &&
                    !startPoint.equals(new Point(x, Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY())))) {
                return new Point(x, Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()));
            }
        }

        // с правой стороной
        if (startPoint.getX() != endPoint.getX()) {
            double m = (endPoint.getY() - startPoint.getY())
                    / (double) (endPoint.getX() - startPoint.getX());
            int y = (int) Math.round(m *
                    (Math.max(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) - startPoint.getX()) +
                    startPoint.getY());
            if (y >= Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) &&
                    y <= Math.max(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) &&
                    !startPoint.equals(new Point(Math.max(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()), y))) {
                return new Point(Math.max(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()), y);
            }
        }

        // с нижней стороны
        if (startPoint.getY() != endPoint.getY()) {
            double m = (endPoint.getY() - startPoint.getY())
                    / (double) (endPoint.getX() - startPoint.getX());
            int x = (int) Math.round(m *
                    (1.0 / m) *
                    (Math.max(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()) - startPoint.getY()));
            if (x >= Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) &&
                    x <= Math.max(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX()) &&
                    !startPoint.equals(new Point(x, Math.max(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY())))) {
                return new Point(x, Math.max(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY()));
            }
        }
        return null;
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
        currentFigure.drawCustomFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                false, rgb, mainCanvas);
    }

    @FXML
    protected void onMainCanvasReleased(MouseEvent mouseEvent) {
        if (currentFigure instanceof Rectangle && rectangle != null) {
            return;
        }

        int rgb = currentFigure instanceof Line ? PixelatedCanvas.getRGBFromColor(Color.BLACK) : PixelatedCanvas.getRGBFromColor(Color.RED);
        currentFigure.drawCustomFigureByEndPoint((int) mouseEvent.getX(), (int) mouseEvent.getY(),
                true, rgb, mainCanvas);
        if (currentFigure instanceof Rectangle) {
            rectangle = currentFigure;
            currentFigure = Figure.getInstance("rectangle");
        } else {
            lines.add(currentFigure);
            currentFigure = Figure.getInstance("line");
        }
    }

    @FXML
    protected void onClearButtonClick() {
        mainCanvas.clear();
        lines = new ArrayList<>();
        rectangle = null;
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
        alert.setContentText("Зеленый - внутри, фиолетовый - снаружи, синий - нужно проверять.");
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
