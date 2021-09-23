package ru.dchertanov.drawdemoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        var screen = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), screen.getWidth() * 2 / 3, screen.getHeight() * 2 / 3);
        stage.setTitle("DrawDemoApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}