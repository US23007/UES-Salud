package ues.ues.salud.model;

import java.time.LocalDate;

/**
 *
 * @author Daniel Lopez LM25002
 */

//Utilizamos la palabra reservada extends para implementar la herencia
public class PacienteEstable extends Paciente{
    
     //Definicion de constructor con parametros
    public PacienteEstable(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sintomas,String sexo, LocalDate fecha_nacimiento,String telefono,String direccion){
        //Con el metodo super referimos al constructor de la clase principal Paciente
        super(id_paciente, nombre_paciente, apellido_paciente, carnet, sintomas,sexo,fecha_nacimiento,telefono,direccion);
    }
    
    //@Override nos permite implementar el concepto de sobreescritura
    
    /*
    @Override
    public String determinarPrioridad(){
        return "Bajo - urgencia menor";
    }
    */
}
