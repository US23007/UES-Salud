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
 * @author US23007 Samuel De Jesús Umaña Sorto
 * Clase EspecialidadDao: Encargada de ser el puente entre nuestra clase base Especialidad y la tabla Especialidad de la base de datos
 */
public class EspecialidadDao implements DaoInterface<Especialidad>{

    //Método para Insertar una Especialidad (No utilizado)
    @Override
    public boolean insertarRegistro(Especialidad entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Método para modificar una Especialidad (No utilizado)
    @Override
    public boolean modificarRegistro(Especialidad entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //Método para eliminar una Especialidad (No utilizado)

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Método para Listar todas las especialidades y asignarlas al comboBox de la vista de Nuevo Triaje

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
    
    //Método para buscar una Especialidad (No utilizado)

    @Override
    public Especialidad buscarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
