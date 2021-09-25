module draw_demo_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens ru.dchertanov.drawdemoapp.figures to javafx.fxml;
    opens ru.dchertanov.drawdemoapp to javafx.fxml;
    opens ru.dchertanov.drawdemoapp.controller to javafx.fxml;
    opens ru.dchertanov.drawdemoapp.util to javafx.fxml;

    exports ru.dchertanov.drawdemoapp;
    exports ru.dchertanov.drawdemoapp.controller;
    exports ru.dchertanov.drawdemoapp.figures;
    exports ru.dchertanov.drawdemoapp.util;
}