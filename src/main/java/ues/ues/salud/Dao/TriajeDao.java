/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.Triaje;

/**
 *
 * @author su487
 */
public class TriajeDao implements DaoInterface<Triaje>{

    @Override
    public boolean insertarRegistro(Triaje entidad) {
        String sql = "{call sp_insertar_triaje(?, ?, ?, ?, ?, ?)}";
        
        try {
            Conexion con = new Conexion();
            
            CallableStatement cs = con.conectar().prepareCall(sql);
       
            cs.setString(1, entidad.getPaciente().getCarnet());
            cs.setString(2, entidad.obtenerEspecialida().getNombreEspecialidad());
            cs.setString(3, entidad.getSintomas()); 
            cs.setDouble(4, entidad.getTemperatura());
            cs.setString(5, entidad.getPresionArterial());
            cs.setString(6, entidad.getNivel_urgencia());

           
            int filasAfectadas = cs.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al ejecutar SP: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarRegistro(Triaje entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Triaje> buscarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Triaje> listarTodos(String campo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
