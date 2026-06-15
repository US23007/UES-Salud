package ues.ues.salud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private int edad;
    private String sexo;
    private int consultas;
    private String telefono;
    private LocalDateTime fecha_nacimiento;
    private String direccion;
    private List<Triaje> triajes = new ArrayList<>();
    
    //Metodo Constructor para acceder a las variable privadas

    public Paciente(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sexo, LocalDateTime fecha_nacimiento,String telefono,String direccion) {
        this.id_paciente = id_paciente;
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.carnet = carnet;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        
    }

    public Paciente(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, int edad, String sexo, int consultas, String telefono, LocalDateTime fecha_nacimiento, String direccion) {
        this.id_paciente = id_paciente;
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.carnet = carnet;
        this.edad = edad;
        this.sexo = sexo;
        this.consultas = consultas;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
    }
    
    

    public Paciente(String nombre_paciente, String apellido_paciente, String carnet, String sexo, String telefono, LocalDateTime fecha_nacimiento, String direccion) {
        this.nombre_paciente = nombre_paciente;
        this.apellido_paciente = apellido_paciente;
        this.carnet = carnet;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
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

    

    public LocalDateTime getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }
    
    

    public String getSexo() {
        return sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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

   

    public void setFecha_nacimiento(LocalDateTime fecha_nacimiento) {
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
    
    
    public void agregarTriaje(Triaje t){
        triajes.add(t);
        t.agregarPaciente(this);
    }
    public List<Triaje> obtenerTriajes(){
        return triajes;
    }

    public int getConsultas() {
        return consultas;
    }

    public void setConsultas(int consultas) {
        this.consultas = consultas;
    }

    public List<Triaje> getTriajes() {
        return triajes;
    }

    public void setTriajes(List<Triaje> triajes) {
        this.triajes = triajes;
    }
    
    
    public Triaje getUltimoTriaje() {
        if (triajes != null && !triajes.isEmpty()) {
            return triajes.get(triajes.size() - 1);
        }
        return null;
    }
    //metodo abstracto que debera ser implementado (sobreescribir) en las clases hijas
   // public abstract String determinarPrioridad();

    @Override
    public String toString() {
        return "Paciente{" + "id_paciente=" + id_paciente + ", nombre_paciente=" + nombre_paciente + ", apellido_paciente=" + apellido_paciente + ", carnet=" + carnet + ", edad=" + edad + ", sexo=" + sexo + ", consultas=" + consultas + ", telefono=" + telefono + ", fecha_nacimiento=" + fecha_nacimiento + ", direccion=" + direccion + '}';
    }

    
    
    
}
