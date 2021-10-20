module ru.dchertanov.fillandclippingdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens ru.dchertanov.fillandclippingdemo.controller to javafx.fxml;

    exports ru.dchertanov.fillandclippingdemo;
    exports ru.dchertanov.fillandclippingdemo.controller;
    exports ru.dchertanov.fillandclippingdemo.figures;
    exports ru.dchertanov.fillandclippingdemo.util;
}