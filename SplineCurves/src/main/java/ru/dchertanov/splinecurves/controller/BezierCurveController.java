package ru.dchertanov.splinecurves.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.dchertanov.splinecurves.util.PixelatedCanvas;

import java.io.IOException;

public class BezierCurveController {
    @FXML
    private PixelatedCanvas mainCanvas;
    @FXML
    private PixelatedCanvas repeatingCanvas;
    @FXML
    private Button backButton;

    public void initialize() {
        MainViewController.configureBackToMainButton(backButton);
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
    protected void onPlusRepeatingCanvasClick() {
        MainViewController.zoom(repeatingCanvas, true);
    }

    @FXML
    protected void onMinusRepeatingCanvasClick() {
        MainViewController.zoom(repeatingCanvas, false);
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
                "Платформа: JavaFX.");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 300);
        alert.showAndWait();
    }
}
