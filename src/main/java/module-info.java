module org.example.ca2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    exports org.example.ca2.app;
    exports org.example.ca2.model;
    exports org.example.ca2.algorithms;
    exports org.example.ca2.graph;

    opens org.example.ca2.app to javafx.fxml;

}