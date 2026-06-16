/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import ues.ues.salud.conexion.Conexion;

/**
 *
 * @author su487
 */
public class ReporteDao {
    public Map<String,Number> obtenerPacientesPorEspecialidad() throws SQLException{
        Map<String,Number> datos = new HashMap<>();
        
        String query = "SELECT "
                + "es.nombre_especialidad, "
                + "count(t.id_triaje) as total "
                + "from triaje t "
                + "INNER JOIN especialidades es  "
                + "ON t.id_especialidad = es.id_especialidad "
                + "group by es.nombre_especialidad "
                + "order by total desc ";
        
        Conexion con = new Conexion();
        Connection conectar = con.conectar();
        
        try{
            PreparedStatement ps = conectar.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                datos.put(rs.getString("nombre_especialidad"), rs.getInt("total"));
            }
            
            return datos;
        }catch(Exception e){
            System.out.println("Error en ReporteDao = " + e.getMessage());
            e.printStackTrace();
        }
        
        return datos;
    }
    
    public Map<String, Integer> obtenerPacientesPorUrgencia() {
        Map<String, Integer> datos = new HashMap<>();
        String sql = "SELECT nivel_urgencia, COUNT(*) as total FROM triaje GROUP BY nivel_urgencia";
        Conexion con = new Conexion();
        try{
             PreparedStatement ps = con.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.put(rs.getString("nivel_urgencia"), rs.getInt("total"));
            }
        } catch (Exception e) {
            System.out.println("Error en reporte urgencias: " + e.getMessage());
        }
        return datos;
    }
    
    
    public Map<String, Integer> obtenerPacientesPorGenero() {
        Map<String, Integer> datos = new HashMap<>();
        
        String sql = "SELECT p.sexo, COUNT(t.id_triaje) as total "
                   + "FROM triaje t "
                   + "INNER JOIN pacientes p ON t.id_paciente = p.id_paciente "
                   + "GROUP BY p.sexo";
        Conexion con = new Conexion();
        
        try{
             PreparedStatement ps = con.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.put(rs.getString("sexo"), rs.getInt("total"));
            }
        } catch (Exception e) {
            System.out.println("Error en reporte género: " + e.getMessage());
        }
        return datos;
    }
    
    public Map<String, Integer> obtenerMedicamentosMasRecetados() {
        Map<String, Integer> datos = new HashMap<>();
        String sql = "SELECT nombre_medicamento, COUNT(*) as total "
                   + "FROM detalles_recetas "
                   + "GROUP BY nombre_medicamento "
                   + "ORDER BY total DESC LIMIT 3";
        Conexion con = new Conexion();
        try{
             PreparedStatement ps = con.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.put(rs.getString("nombre_medicamento"), rs.getInt("total"));
            }
        } catch (Exception e) {
            System.out.println("Error en reporte medicamentos: " + e.getMessage());
        }
        return datos;
    }
            
}
