module ues.ues.salud {
    //Módulo, paquetes y librerias para que javaFx pueda utilizarlas libremente
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.controlsfx.controls;
    requires itextpdf;
    requires java.desktop;
    requires org.jfree.jfreechart;
    opens ues.ues.salud to javafx.fxml;
    opens ues.ues.salud.model  to javafx.fxml,javafx.base;
    exports ues.ues.salud;
}
