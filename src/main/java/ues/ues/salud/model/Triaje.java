package ues.ues.salud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel López LM25002
 */
public class Triaje {
    
    //Atributos
    private int id_triaje;
    private Paciente paciente;
    
    private String sintomas;
    private double temperatura;
    private String presionArterial;
    private String nivel_urgencia;
    private LocalDateTime fecha_registro;
    private Especialidad especialidad;

    
    //definicion del constructor

    public Triaje(Paciente paciente, String sintomas, double temperatura, String presionArterial, String nivel_urgencia, LocalDateTime fecha_registro, Especialidad especialidad) {
        this.paciente = paciente;
        this.sintomas = sintomas;
        this.temperatura = temperatura;
        this.presionArterial = presionArterial;
        this.nivel_urgencia = nivel_urgencia;
        this.fecha_registro = fecha_registro;
        this.especialidad = especialidad;
    }
    
    

    public Triaje() {
    }
    
    
    //Metodos getters y setters
    public int getId_triaje() {
        return id_triaje;
    }

    public void setId_triaje(int id_triaje) {
        this.id_triaje = id_triaje;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

   
    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public String getNivel_urgencia() {
        return nivel_urgencia;
    }

    public void setNivel_urgencia(String nivel_urgencia) {
        this.nivel_urgencia = nivel_urgencia;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    public void agregarPaciente(Paciente p){
        this.paciente = p;
        
    }
    public Paciente obtenerPaciente(){
        return paciente;
    }
    
    public void agregarEspecialidad(Especialidad e){
        this.especialidad = e;
    }
    
    public Especialidad obtenerEspecialida(){
        return especialidad;
    }
    @Override
    public String toString() {
        return "Triaje{" + "id_triaje=" + id_triaje + ", paciente=" + paciente  + ", sintomas=" + sintomas + ", temperatura=" + temperatura + ", presionArterial=" + presionArterial + ", nivel_urgencia=" + nivel_urgencia + ", fecha_registro=" + fecha_registro + '}';
    }
    
    
    
}
