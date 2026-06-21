
package ues.ues.salud.model;

//importando paquetes necesarios
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Guillermo Daniel Lopez Montenegro LM25002
 * Esta clase almacena informacion sobre quien emitio la receta, el triaje o la enfermedad que se detecto
 * Esta clase es importante para facilitar los CRUD en los DAO
 */
public class Receta {
    //declarando atributos
    private int idReceta;
    private Triaje triaje;
    private Doctor doctor;
    private String diagnostico;
    private List<DetalleReceta> detalles; //Una receta contiene muchos detalles. Asociacion 1 a muchos
    private LocalDateTime fechaEmision;

    //Metodo constructor con parametros para acceder a las variables privadas
    public Receta(Triaje triaje, Doctor doctor, String diagnostico, LocalDateTime fechaEmision) {
        this.triaje = triaje;
        this.doctor = doctor;
        this.diagnostico = diagnostico;
        this.fechaEmision = fechaEmision;
    }

    public Receta() {
    }

    //Metodos getters y setters
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
