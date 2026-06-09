module ues.ues.salud {
    requires javafx.controls;
    requires javafx.fxml;

    opens ues.ues.salud to javafx.fxml;
    exports ues.ues.salud;
}
