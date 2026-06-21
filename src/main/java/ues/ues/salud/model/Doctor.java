/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.model;

/**
 *
 * @author Guillermo Daniel Lopez Montenegro LM25002
 * Esta clase guarda toda la informacion de los doctores del sistema
 * Es de gran utilidad para facilitar las operaciones CRUD en las DAO
 */

//Definicion de la clase
public class Doctor {
    //Declarando atributos 
    private int idDoctor;
    private String nombre;
    private String apellidos;
    private Especialidad Especialidad;
    private String juntaVigilancia;

    //Metodos constructor
    public Doctor() {
    }

    //Constructor con parametros para acceder a las variables privadas
    public Doctor(int idDoctor, String nombre, String apellidos, Especialidad Especialidad, String juntaVigilancia) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.Especialidad = Especialidad;
        this.juntaVigilancia = juntaVigilancia;
    }

    public Doctor(String nombre, String apellidos, Especialidad Especialidad, String juntaVigilancia) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.Especialidad = Especialidad;
        this.juntaVigilancia = juntaVigilancia;
    }

    //Metodos getters y setters
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Especialidad getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(Especialidad Especialidad) {
        this.Especialidad = Especialidad;
    }

    public String getJuntaVigilancia() {
        return juntaVigilancia;
    }

    public void setJuntaVigilancia(String juntaVigilancia) {
        this.juntaVigilancia = juntaVigilancia;
    }

    @Override
    public String toString() {
        return "Doctor{" + "idDoctor=" + idDoctor + ", nombre=" + nombre + ", apellidos=" + apellidos + ", Especialidad=" + Especialidad + ", juntaVigilancia=" + juntaVigilancia + '}';
    }
    
    
    
}
