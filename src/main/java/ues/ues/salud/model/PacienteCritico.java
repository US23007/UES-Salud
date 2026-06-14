package ues.ues.salud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel López LM25002
 */

//Utilizamos la palabra reservada extends para implementar la herencia
public class PacienteCritico extends Paciente{

    //Definicion de constructor con parametros
    public PacienteCritico(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sexo, LocalDateTime fecha_nacimiento,String telefono,String direccion) {
       //Con el metodo super referimos al constructor de la clase principal Paciente
        super(id_paciente, nombre_paciente, apellido_paciente, carnet, sexo, fecha_nacimiento,telefono,direccion);
    }
    
    
    
    
    
    //@Override nos permite implementar el concepto de sobreescritura
    
    /*
    @Override
    public String determinarPrioridad(){
        return "Alto - El paciente requiere atencion inmediatamente";
    }
    */

}
