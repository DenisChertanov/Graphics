module ru.dchertanov.polygons3D {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens ru.dchertanov.polygons3D.controller to javafx.fxml;
//
    exports ru.dchertanov.polygons3D;
//    exports ru.dchertanov.fillandclippingdemo.controller;
//    exports ru.dchertanov.fillandclippingdemo.figures;
//    exports ru.dchertanov.fillandclippingdemo.util;
}