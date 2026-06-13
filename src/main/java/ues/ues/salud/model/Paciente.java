package ues.ues.salud.model;

import java.time.LocalDate;

/**
 *
 * @author Daniel López LM25002
 */
//definicion de una clase abstracta que no se puede instanciar
public class Paciente {
    
    //Atributos del Paciente
    private int id_paciente;
    private String nombre_paciente;
    private String apellido_paciente;
    private String carnet;
    private String sintomas;
    private String sexo;
    private String telefono;
    private LocalDate fecha_nacimiento;
    private String direccion;
    
    //Metodo Constructor para acceder a las variable privadas

    public Paciente(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sintomas, String sexo, LocalDate fecha_nacimiento,String telefono,String direccion) {
        this.id_paciente = id_paciente;
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.carnet = carnet;
        this.sintomas = sintomas;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Paciente() {
    }
    
    
    
    //Implementacion de Metodos Getters
    public int getId_paciente() {
        return id_paciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public String getApellido_paciente() {
        return apellido_paciente;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getSintomas() {
        return sintomas;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }
    
    

    public String getSexo() {
        return sexo;
    }
    
    
     //Implementacion de Metodos Setters
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public void setApellido_paciente(String apellido_paciente) {
        this.apellido_paciente = apellido_paciente;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
    //metodo abstracto que debera ser implementado (sobreescribir) en las clases hijas
   // public abstract String determinarPrioridad();

    @Override
    public String toString() {
        return "Paciente{" + "id_paciente=" + id_paciente + ", nombre_paciente=" + nombre_paciente + ", apellido_paciente=" + apellido_paciente + ", carnet=" + carnet + ", sintomas=" + sintomas + ", fecha_nacimiento=" + fecha_nacimiento + '}';
    }
    
    
}
