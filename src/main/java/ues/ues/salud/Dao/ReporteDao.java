/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
        
        String sql = "SELECT p.sexo, COUNT(DISTINCT t.id_paciente) as total "
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
    
    public List<String[]> obtenerAtencionesPorFecha(LocalDate inicio, LocalDate fin) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT t.fecha_registro, p.carnet, CONCAT(p.nombres, ' ', p.apellidos) AS paciente, "
                + "e.nombre_especialidad, t.nivel_urgencia "
                + "FROM triaje t "
                + "INNER JOIN pacientes p ON t.id_paciente = p.id_paciente "
                + "INNER JOIN especialidades e ON t.id_especialidad = e.id_especialidad "
                + "WHERE t.fecha_registro BETWEEN ? AND ? "
                + "ORDER BY t.fecha_registro DESC";

        try {
            Conexion con = new Conexion();
            Connection conectar = con.conectar();
            
            java.sql.Timestamp tsInicio = java.sql.Timestamp.valueOf(inicio.atStartOfDay());
            java.sql.Timestamp tsFin = java.sql.Timestamp.valueOf(fin.atTime(23, 59, 59));
            
            PreparedStatement ps = conectar.prepareStatement(sql);
            
            ps.setTimestamp(1, (tsInicio));
            ps.setTimestamp(2, (tsFin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new String[]{
                        rs.getString("fecha_registro"),
                        rs.getString("carnet"),
                        rs.getString("paciente"),
                        rs.getString("nombre_especialidad"),
                        rs.getString("nivel_urgencia")
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en reporte cronológico: " + e.getMessage());
        }
        return lista;
    }
    
    
    public List<String[]> obtenerHistorialPorCarnet(String carnet) {
        List<String[]> historial = new ArrayList<>();
        String sql = "SELECT t.fecha_registro, e.nombre_especialidad, t.sintomas, t.nivel_urgencia "
                + "FROM triaje t "
                + "INNER JOIN pacientes p ON t.id_paciente = p.id_paciente "
                + "INNER JOIN especialidades e ON t.id_especialidad = e.id_especialidad " // Ajusta si tu tabla es 'especialidades'
                + "WHERE p.carnet = ? "
                + "ORDER BY t.fecha_registro DESC";

        try {
            Conexion con = new Conexion();
            Connection conexion = con.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, carnet);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(new String[]{
                        rs.getString("fecha_registro"),
                        rs.getString("nombre_especialidad"),
                        rs.getString("sintomas"),
                        rs.getString("nivel_urgencia")
                    });
                }
            }
        } catch (SQLException e) {
            Notifications.create()
                        .title("Operación Fallida")
                        .text("El Carnet Ingresado es Invalido o no Existe")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showError();
            
        }
        return historial;
    }
    
    
    public List<String[]> obtenerSintomasMasComunes(LocalDate inicio, LocalDate fin) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT sintomas, COUNT(*) AS total_repeticiones "
                + "FROM triaje "
                + "WHERE fecha_registro BETWEEN ? AND ? "
                + "GROUP BY sintomas "
                + "ORDER BY total_repeticiones DESC";

        try  {
            Conexion con = new Conexion();
            Connection conexion = con.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(inicio.atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(fin.atTime(23, 59, 59)));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new String[]{
                        rs.getString("sintomas"),
                        rs.getString("total_repeticiones")
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en reporte estadístico de síntomas: " + e.getMessage());
        }
        return lista;
    }
            
}
