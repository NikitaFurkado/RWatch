module com.example.rwatch {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires mysql.connector.j;

    opens com.example.rwatch to javafx.fxml;
    exports com.example.rwatch;
    exports com.example.rwatch.controllers;
    opens com.example.rwatch.controllers to javafx.fxml;
}