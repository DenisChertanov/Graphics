module ru.dchertanov.fillandclippingdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens ru.dchertanov.splinecurves.controller to javafx.fxml;

    exports ru.dchertanov.splinecurves;
    exports ru.dchertanov.splinecurves.controller;
    exports ru.dchertanov.splinecurves.figures;
    exports ru.dchertanov.splinecurves.util;
}