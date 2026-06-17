/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.DetalleReceta;
import ues.ues.salud.model.Especialidad;
import ues.ues.salud.model.Paciente;
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
        List<Triaje> historial = new ArrayList<>();
        Conexion con = new Conexion();
        String query = "SELECT "
                + "    p.carnet, "
                + "    p.nombres as Nombres, "
                + "    p.apellidos as Apellidos, "
                + "    TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) AS Edad, "
                + "    p.sexo AS Género, "
                + "    e.nombre_especialidad AS Especialidad, "
                + "    t.fecha_registro AS Fecha_Consulta, "
                + "    t.sintomas AS Sintomas, "
                +"     t.temperatura AS Temperatura, "
                + "    t.presion_arterial AS Presión, "
                + "    t.nivel_urgencia AS Urgencia "
                + "FROM pacientes p "
                + "INNER JOIN triaje t ON p.id_paciente = t.id_paciente "
                + "INNER JOIN especialidades e ON t.id_especialidad = e.id_especialidad "
                + "ORDER BY t.fecha_registro DESC";
        try {
            PreparedStatement ps = con.conectar().prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setCarnet(rs.getString("carnet"));
                p.setNombre_paciente(rs.getString("Nombres"));
                p.setApellido_paciente(rs.getString("Apellidos"));
                p.setSexo(rs.getString("Género"));
                p.setEdad(rs.getInt("Edad"));

                Especialidad e = new Especialidad();
                e.setNombreEspecialidad(rs.getString("Especialidad"));

                Triaje t = new Triaje();
                t.setFecha_registro(rs.getTimestamp("Fecha_Consulta").toLocalDateTime());
                t.setTemperatura(rs.getDouble("temperatura"));
                t.setSintomas(rs.getString("Sintomas"));
                t.setPresionArterial(rs.getString("Presión"));
                t.setNivel_urgencia(rs.getString("Urgencia"));

                
                t.agregarPaciente(p);
                t.agregarEspecialidad(e);

                historial.add(t); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historial;
    }
    
    
    
    public int insertarRegistroConId(Triaje triaje) {
        String sql = "{call sp_insertar_triaje(?, ?, ?, ?, ?, ?)}";

        try {
            Conexion con = new Conexion();

            CallableStatement cs = con.conectar().prepareCall(sql);

            cs.setString(1, triaje.getPaciente().getCarnet());
            cs.setString(2, triaje.obtenerEspecialida().getNombreEspecialidad());
            cs.setString(3, triaje.getSintomas());
            cs.setDouble(4, triaje.getTemperatura());
            cs.setString(5, triaje.getPresionArterial());
            cs.setString(6, triaje.getNivel_urgencia());

            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar SP: " + e.getMessage());

        }
        
        return 0;
    }
    
}
