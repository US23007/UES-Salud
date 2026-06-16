module ues.ues.salud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.controlsfx.controls;
    requires itextpdf;
    requires java.desktop;
    opens ues.ues.salud to javafx.fxml;
    opens ues.ues.salud.model  to javafx.fxml,javafx.base;
    exports ues.ues.salud;
}
