package ru.dchertanov.fillandclippingdemo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
}
