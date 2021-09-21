module ru.dchertanov.drawdemoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;
    requires java.desktop;
    requires javafx.swing;

    requires org.controlsfx.controls;

    opens ru.dchertanov.drawdemoapp to javafx.fxml;
    exports ru.dchertanov.drawdemoapp;
    exports ru.dchertanov.drawdemoapp.controller;
    opens ru.dchertanov.drawdemoapp.controller to javafx.fxml;
}