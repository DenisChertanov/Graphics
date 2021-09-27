package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.dchertanov.fillandclippingdemo.MainApplication;

import java.io.IOException;

public class MainViewController {
    @FXML
    private GridPane gridPane;

    private void loadScene(String sceneName) throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onFillFigureClick() throws IOException {
        loadScene("fill-figure-view.fxml");
    }

    @FXML
    protected void onFillPolygonClick() throws IOException {
        loadScene("fill-polygons-view.fxml");
    }

    @FXML
    protected void onClippingClick() throws IOException {
        loadScene("clipping-view.fxml");
    }

    public static void backToMainClick(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }

    public static void configureBackToMainButton(Button backButton) {
        javafx.scene.image.Image image = new Image("back_arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        backButton.setGraphic(imageView);
    }

    public static void zoom(Canvas canvas, boolean increase) {
        if (increase) {
            canvas.getTransforms().add(new Scale(1.5, 1.5));
        } else {
            canvas.getTransforms().add(new Scale(1 / 1.5, 1 / 1.5));
        }
    }
}
