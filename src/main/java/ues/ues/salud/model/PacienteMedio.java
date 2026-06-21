package ues.ues.salud.model;

//importando paquetes necesarios
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel Lopez LM25002
 * Esta clase implementa el proceso de sobreescritura y sirve para representar un tipo 
 * especifico del paciente que por su condicion requiere de una atencion de MEDIA prioridad
 */

//Utilizamos la palabra reservada extends para implementar la herencia
public class PacienteMedio extends Paciente{
    
    //Definicion de constructor con parametros
    public PacienteMedio(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet,String sexo, LocalDateTime fecha_nacimiento,String telefono,String direccion){
        //Con el metodo super referimos al constructor de la clase principal Paciente
        super(id_paciente, nombre_paciente, apellido_paciente, carnet,sexo, fecha_nacimiento,telefono,direccion);
    }
    
    
}
