/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.Doctor;
import ues.ues.salud.model.Especialidad;

/**
 *
 * @author su487
 */
public class DoctorDao implements DaoInterface<Doctor>{

    @Override
    public boolean insertarRegistro(Doctor entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificarRegistro(Doctor entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Doctor buscarRegistro(String codigo) {
        Doctor d = new Doctor();
        Conexion con = new Conexion();
        String query = "SELECT "
                + "id_doctor as ID "
                + "FROM doctores WHERE concat(nombre_doctor,' ',apellido_doctor) = ? ";
        try{
            PreparedStatement ps = con.conectar().prepareStatement(query);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                d.setIdDoctor(rs.getInt("ID"));
                
            }
            return d;
        }catch(Exception e){
            System.out.println("Algo Salio Mal en Buscar DoctorDao = " + e.getMessage());
            e.printStackTrace();
        }
        
        return d;
    }

    @Override
    public List<Doctor> listarTodos(String campo, String valor) {
        List<Doctor> doctores = new ArrayList<>();
        String query = "SELECT "
                + "concat(nombre_doctor,' ',apellido_doctor) as Atentido, "
                + "nombre_especialidad as Especialidad "
                + "FROM doctores d "
                + "INNER JOIN especialidades es "
                + "ON d.id_especialidad = es.id_especialidad "
                + "WHERE  1=1 ";
        
        if (campo != null && !campo.trim().isEmpty()) {
            // SQL limpio: ... WHERE nombre_especialidad = ?
            query += " AND " + campo + " = ? ";
        }
        Conexion con = new Conexion();
        try{
            PreparedStatement ps = con.conectar().prepareStatement(query);
            if (campo != null && !campo.trim().isEmpty()) {
                ps.setString(1, valor);
            }
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Doctor d = new Doctor();
                d.setNombre(rs.getString("Atentido"));
                
                Especialidad e = new  Especialidad();
                e.setNombreEspecialidad(rs.getString("Especialidad"));
                
                d.setEspecialidad(e);
                
                doctores.add(d);
            
            }
            return doctores;
        }catch(Exception e){
            System.out.println("Algo Salio Mal en listar Todos DoctorDao = " + e.getMessage());
            e.printStackTrace();
        }
        
        return doctores;
    }
    
    

    public int ObtenerDoctor(String codigo) {
        int ID = 0;
        Conexion con = new Conexion();
        String query = "SELECT "
                + "id_doctor as ID "
                + "FROM doctores WHERE concat(nombre_doctor,' ',apellido_doctor) = ? ";
        try{
            PreparedStatement ps = con.conectar().prepareStatement(query);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ID = rs.getInt("ID");
            }
            return ID;
        }catch(Exception e){
            System.out.println("Algo Salio Mal en Buscar DoctorDao = " + e.getMessage());
            e.printStackTrace();
        }
        
        return ID;
    }
    
}
