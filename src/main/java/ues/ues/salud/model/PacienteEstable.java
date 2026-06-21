package ues.ues.salud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Guillermo Daniel Lopez Montenegro LM25002
 * Esta clase implementa el proceso de sobreescritura y sirve para representar un tipo 
 * especifico del paciente que por su condicion requiere de una atencion BAJA de prioridad
 */

//Utilizamos la palabra reservada extends para implementar la herencia
public class PacienteEstable extends Paciente{
    
     //Definicion de constructor con parametros
    public PacienteEstable(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet,String sexo, LocalDateTime fecha_nacimiento,String telefono,String direccion){
        //Con el metodo super referimos al constructor de la clase principal Paciente
        super(id_paciente, nombre_paciente, apellido_paciente, carnet,sexo,fecha_nacimiento,telefono,direccion);
    }
    
}
