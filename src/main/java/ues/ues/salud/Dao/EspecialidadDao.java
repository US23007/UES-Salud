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
import ues.ues.salud.model.Especialidad;

/**
 *
 * @author Samuel
 */
public class EspecialidadDao implements DaoInterface<Especialidad>{

    @Override
    public boolean insertarRegistro(Especialidad entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificarRegistro(Especialidad entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Especialidad> buscarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Especialidad> listarTodos(String campo, String valor) {
        List<Especialidad> especialidades = new ArrayList<>();
        String query = "SELECT nombre_especialidad from especialidades";
        Conexion con = new Conexion();
        try{
            PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Especialidad es = new Especialidad();
                es.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                especialidades.add(es);
            
            }
            return especialidades;
        }catch(Exception e){
            System.out.println("Algo Salio Mal en listar Todos EspecialidadDao = " + e.getMessage());
            e.printStackTrace();
        }
        
        return especialidades;
    }
    
}
