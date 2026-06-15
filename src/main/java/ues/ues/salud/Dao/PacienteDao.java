/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javax.xml.transform.Result;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.Especialidad;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.Triaje;

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
        List<Paciente> pacientes = new ArrayList<>();
        try {
            Conexion con = new Conexion();
            String query = "SELECT "
                    +"p.id_paciente, "
                    + "carnet, "
                    + "nombres, "
                    + "apellidos, "
                    + "TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) AS Edad, "
                    + "sexo AS Género, "
                    + "p.telefono as Télefono, "
                    + "p.direccion as Dirección, "
                    + "COUNT(t.id_triaje) AS Consultas "
                    + "FROM pacientes p "
                    + "INNER JOIN triaje t "
                    + "ON p.id_paciente = t.id_paciente "
                    + "WHERE carnet = ? "
                    + "GROUP BY p.id_paciente, p.carnet, p.nombres, p.apellidos, p.fecha_nacimiento, p.sexo , p.telefono, p.direccion";

            PreparedStatement ps = con.conectar().prepareStatement(query);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId_paciente(rs.getInt("id_paciente"));
                p.setCarnet(rs.getString("carnet"));
                p.setNombre_paciente(rs.getString("nombres"));
                p.setApellido_paciente(rs.getString("apellidos"));
                p.setEdad(rs.getInt("Edad"));
                p.setSexo(rs.getString("Género"));
                p.setTelefono(rs.getString("Télefono"));
                p.setDireccion(rs.getString("Dirección"));
                p.setConsultas(rs.getInt("Consultas"));

                pacientes.add(p);

            }

            return pacientes;

        } catch (Exception e) {
            System.out.println("Error en Cargar pacientes en PacienteDao listarTodos  = " + e);
            e.printStackTrace();
        }

        return pacientes;
    }

    @Override
    public List<Paciente> listarTodos(String campo, String valor) {
        List<Paciente> pacientes = new ArrayList<>();
         try{
            Conexion con = new Conexion();
             String query = "SELECT "
                     + "		p.carnet as Carnet, "
                     + "		CONCAT(p.nombres, ' ', p.apellidos) AS Nombre_Completo, "
                     + "    TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) AS Edad, "
                     + "		p.sexo AS Género, "
                     + "		COUNT(t.id_triaje) AS Consultas "
                     + "	FROM pacientes p "
                     + "		INNER JOIN triaje t ON p.id_paciente = t.id_paciente "
                     + "	GROUP BY p.id_paciente, p.carnet, p.nombres, p.apellidos, p.fecha_nacimiento, p.sexo; ";
            
            
            
             PreparedStatement ps = con.conectar().prepareStatement(query);
             ResultSet  rs = ps.executeQuery();
            
             while (rs.next()) {
                 Paciente p = new Paciente();
                 p.setCarnet(rs.getString("Carnet"));
                 p.setNombre_paciente(rs.getString("Nombre_Completo"));
                 p.setSexo(rs.getString("Género"));
                 p.setEdad(rs.getInt("Edad"));
                 p.setConsultas(rs.getInt("Consultas"));
                 
                 pacientes.add(p);

             }
             
             return pacientes;

            
        }catch(Exception e){
            System.out.println("Error en Cargar pacientes en PacienteDao listarTodos  = " + e);
            e.printStackTrace();
        }
        
        return pacientes;
    }
    
}
