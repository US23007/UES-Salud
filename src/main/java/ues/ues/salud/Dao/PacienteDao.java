/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.PreparedStatement;
import java.util.List;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.Paciente;

/**
 *
 * @author su487
 */
public class PacienteDao implements DaoInterface<Paciente>{

    @Override
    public boolean insertarRegistro(Paciente entidad) {
        try{
            Conexion con = new Conexion();
            String query = "INSERT INTO pacientes(carnet,nombres,apellidos,fecha_nacimiento,sexo,telefono,direccion) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.conectar().prepareStatement(query);
            ps.setString(1,entidad.getCarnet());
            ps.setString(2,entidad.getNombre_paciente());
            ps.setString(3,entidad.getApellido_paciente());
            ps.setTimestamp(4,java.sql.Timestamp.valueOf(entidad.getFecha_nacimiento()));
            ps.setString(5, entidad.getSexo());
            ps.setString(6, entidad.getTelefono());
            ps.setString(7, entidad.getDireccion());
            
            int fila = ps.executeUpdate();
            if(fila>0){
                Notifications.create()
                    .title("Proceso Completado")
                    .text("Paciente Registrado Correctamente en la Base de Datos")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showInformation();
                return true;
            }
            
            
        }catch(Exception e){
            System.out.println("Error en Insertar Registro Paciente  = " + e);
            e.getMessage();
        }
        
        return false;
    }

    @Override
    public boolean modificarRegistro(Paciente entidad) {
        try{
            Conexion con = new Conexion();
            String query = "UPDATE pacientes SET nombres=?,apellidos=?,fecha_nacimiento=?,sexo=?,telefono=?,direccion=? WHERE carnet=?";
            PreparedStatement ps = con.conectar().prepareStatement(query);
            
            ps.setString(1,entidad.getNombre_paciente());
            ps.setString(2,entidad.getApellido_paciente());
            ps.setTimestamp(3,java.sql.Timestamp.valueOf(entidad.getFecha_nacimiento()));
            ps.setString(4, entidad.getSexo());
            ps.setString(5, entidad.getTelefono());
            ps.setString(6, entidad.getDireccion());
            ps.setString(7,entidad.getCarnet());
            int fila = ps.executeUpdate();
            if(fila>0){
                Notifications.create()
                    .title("Proceso Completado")
                    .text("Paciente Actualizado Correctamente en la Base de Datos")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showInformation();
                return true;
            }
            
            
        }catch(Exception e){
            System.out.println("Error en Insertar Registro Paciente  = " + e);
            e.getMessage();
        }
        
        return false;
    }

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Paciente> buscarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Paciente> listarTodos(String campo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
