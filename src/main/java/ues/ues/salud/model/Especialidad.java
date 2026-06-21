/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.model;

/**
 *
 * @author Guillermo Daniel Lopez Montenegro LM25002
 * Esta clase registra las especialidades disponibles del sistema hospitalario
 * Esta clase es util para facilitar las operaciones CRUD en los DAO
 */

//Definicion de la clase
public class Especialidad {
    
    //Declaracion de atributos
    private String nombreEspecialidad;
    private Triaje triaje ;
    
    //Metodos constructor para acceder a la variable privada
    public Especialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Especialidad() {
    }

    //Metodos Getters y setters
    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
    
    
    public void agregarTriaje(Triaje t){
        this.triaje = t;
        t.agregarEspecialidad(this);
    }
    
    public Triaje obtenerTriaje(){
        return triaje;
    }
    
    
}
