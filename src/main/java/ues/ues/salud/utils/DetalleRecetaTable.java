
package ues.ues.salud.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Clase DetalleRecetaTable : es el controlador de una tabla que puede ser reutilizada para agregar los medicamentos de los pacientes
 * @author US23007 Samuel De Jesus Umaña Sorto 
 */
public class DetalleRecetaTable {
    //Columnas de la tabla 
    private final StringProperty nombre = new SimpleStringProperty("");
    private final StringProperty dosis = new SimpleStringProperty("");
    private final StringProperty indicaciones = new SimpleStringProperty("");

    
    //Constructor
    public DetalleRecetaTable() {
    }
    
    //Constructor con parametros para asignar variables
    public DetalleRecetaTable(String nombre, String dosis, String indicaciones) {
        setNombre(nombre);
        setDosis(dosis);
        setIndicaciones(indicaciones);
    }
    
    //Setters y Getters 
    
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
