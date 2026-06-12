package ues.ues.salud.model;

import java.time.LocalDate;

/**
 *
 * @author Daniel López LM25002
 */

//Utilizamos la palabra reservada extends para implementar la herencia
public class PacienteCritico extends Paciente{
    
    //Definicion de constructor con parametros
    public PacienteCritico(int id_paciente, String nombre_paciente, String apellido_paciente, String carnet, String sintomas, LocalDate fecha_nacimiento){
        //Con el metodo super referimos al constructor de la clase principal Paciente
        super(id_paciente, nombre_paciente, apellido_paciente, carnet, sintomas, fecha_nacimiento);
    }
    
    
    //@Override nos permite implementar el concepto de sobreescritura
    @Override
    public String determinarPrioridad(){
        return "Alto - El paciente requiere atencion inmediatamente";
    }
}
