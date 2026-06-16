/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author su487
 */
public class DetalleRecetaTable {
    private final StringProperty nombre = new SimpleStringProperty("");
    private final StringProperty dosis = new SimpleStringProperty("");
    private final StringProperty indicaciones = new SimpleStringProperty("");

    public DetalleRecetaTable() {
    }
    
    public DetalleRecetaTable(String nombre, String dosis, String indicaciones) {
        setNombre(nombre);
        setDosis(dosis);
        setIndicaciones(indicaciones);
    }
    
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty dosisProperty() { return dosis; }
    public StringProperty indicacionesProperty() { return indicaciones; }
    
    public String getNombre() { return nombre.get(); }
    public void setNombre(String val) { nombre.set(val); }
    public String getDosis() { return dosis.get(); }
    public void setDosis(String val) { dosis.set(val); }
    public String getIndicaciones() { return indicaciones.get(); }
    public void setIndicaciones(String val) { indicaciones.set(val); }
    
}
