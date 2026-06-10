module ues.ues.salud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ues.ues.salud to javafx.fxml;
    exports ues.ues.salud;
}
