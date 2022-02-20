module hu.petrik.muzeumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires com.google.gson;


    opens hu.petrik.muzeumfrontendjavafx to javafx.fxml, com.google.gson;
    exports hu.petrik.muzeumfrontendjavafx;
    exports hu.petrik.muzeumfrontendjavafx.controllers;
    opens hu.petrik.muzeumfrontendjavafx.controllers to javafx.fxml;
}