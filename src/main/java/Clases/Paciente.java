package Clases;

import java.time.LocalDate;

/**
 *
 * @author Daniel López LM25002
 */
//definicion de una clase abstracta que no se puede instanciar
public abstract class Paciente {
    
    //Atributos del Paciente
    private int id_paciente;
    private String nombre_paciente;
    private String apellido_paciente;
    private String carnet;
    private String sintomas;
    private LocalDate fecha_nacimiento;
    
    //Metodo Constructor para acceder a las variable privadas
    public Paciente(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sintomas, LocalDate fecha_nacimiento) {
        this.id_paciente = id_paciente;
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.carnet = carnet;
        this.sintomas = sintomas;
        this.fecha_nacimiento = fecha_nacimiento;
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
    
    //metodo abstracto que debera ser implementado (sobreescribir) en las clases hijas
    public abstract String determinarPrioridad();

    @Override
    public String toString() {
        return "Paciente{" + "id_paciente=" + id_paciente + ", nombre_paciente=" + nombre_paciente + ", apellido_paciente=" + apellido_paciente + ", carnet=" + carnet + ", sintomas=" + sintomas + ", fecha_nacimiento=" + fecha_nacimiento + '}';
    }
    
    
}
