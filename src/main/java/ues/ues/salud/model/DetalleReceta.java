/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.model;

/**
 *
 * @author Guillermo Daniel Lopez Montenegro LM25002
 * Esta clase registra los medicamentos, dosis o indicaciones especificos que le recetan al paciente
 */
public class DetalleReceta {
    //Declaracion de atributos
    private int idDetalleReceta;
    private Receta receta;
    private String nombre_medicamento;
    private String dosis;
    private String indicaciones;

    //Metodos constructor
    public DetalleReceta() {
    }

    //Metodo constructor con parametros para acceder a las variables privadas
    public DetalleReceta(String nombre_medicamento, String dosis, String indicaciones) {
        this.nombre_medicamento = nombre_medicamento;
        this.dosis = dosis;
        this.indicaciones = indicaciones;
    }

    //metodos getters y setters
    public int getIdDetalleReceta() {
        return idDetalleReceta;
    }

    public void setIdDetalleReceta(int idDetalleReceta) {
        this.idDetalleReceta = idDetalleReceta;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getNombre_medicamento() {
        return nombre_medicamento;
    }

    public void setNombre_medicamento(String nombre_medicamento) {
        this.nombre_medicamento = nombre_medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    @Override
    public String toString() {
        return "DetalleReceta{" + "idDetalleReceta=" + idDetalleReceta + ", receta=" + receta + ", nombre_medicamento=" + nombre_medicamento + ", dosis=" + dosis + ", indicaciones=" + indicaciones + '}';
    }
    
    
    
}
