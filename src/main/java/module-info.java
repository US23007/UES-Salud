module ues.ues.salud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens ues.ues.salud to javafx.fxml;
    exports ues.ues.salud;
}
