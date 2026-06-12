module ues.ues.salud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.controlsfx.controls;
    opens ues.ues.salud to javafx.fxml;
    exports ues.ues.salud;
}
