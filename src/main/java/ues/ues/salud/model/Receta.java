/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author su487
 */
public class Receta {
    private int idReceta;
    private Triaje triaje;
    private Doctor doctor;
    private String diagnostico;
    private List<DetalleReceta> detalles;
    private LocalDateTime fechaEmision;

    public Receta(Triaje triaje, Doctor doctor, String diagnostico, LocalDateTime fechaEmision) {
        this.triaje = triaje;
        this.doctor = doctor;
        this.diagnostico = diagnostico;
        this.fechaEmision = fechaEmision;
    }

    

    

    public Receta() {
    }

    public Triaje getTriaje() {
        return triaje;
    }

    public void setTriaje(Triaje triaje) {
        this.triaje = triaje;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    private void agregarDetalle(DetalleReceta d){
        detalles.add(d);
    }
    
    private List<DetalleReceta> obtenerDetalles(){
        return detalles;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Receta{" + "idReceta=" + idReceta + ", triaje=" + triaje + ", doctor=" + doctor + ", diagnostico=" + diagnostico + ", fechaEmision=" + fechaEmision + '}';
    }

    
    
    
    
    
}
