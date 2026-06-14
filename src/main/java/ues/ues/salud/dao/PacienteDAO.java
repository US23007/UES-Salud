package ues.ues.salud.dao;

/**
 *
 * @author Daniel López LM25002
 */

//importando paquetes o librerias necesarias para trabajar
import java.sql.*;
import java.util.*;
import java.time.*;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.*;

public class PacienteDAO {
    
    //instanciamos la conexion y paciente
    Conexion c = new Conexion();
    Paciente paciente = new Paciente();
    
    //metodo para insertar un paciente
    public boolean insertar(Paciente paciente) throws SQLException{
        
        //la variable sql almacena el comando para insertar
        String sql = "INSERT INTO Paciente(id_paciente, nombre_paciente, apellido_paciente, carnet, sintomas, sexo, telefono, fecha_nacimiento, direccion) VALUES(?,?,?,?,?,?,?,?,?)";
        
        //Obtenemos la conexion
        try(Connection conn = c.conectar()){
            
            //verifica si no se pudo conectar
            if (conn == null){
                throw new SQLException("No se pudo conectar a la base de datos, favor revisar sus credenciales");
            }
            
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, paciente.getId_paciente());
                ps.setString(2, paciente.getNombre_paciente());
                ps.setString(3, paciente.getApellido_paciente());
                ps.setString(4, paciente.getCarnet());
                ps.setString(5, paciente.getSintomas());
                ps.setString(6, paciente.getSexo());
                ps.setString(7, paciente.getTelefono());
                ps.setDate(8, java.sql.Date.valueOf(paciente.getFecha_nacimiento()));
                ps.setString(9, paciente.getDireccion());
                
                ps.executeUpdate();
            }
        }
        return false;
    }
    
    //metodo para actualizar un registro paciente
    public boolean actualizar(Paciente paciente) throws SQLException{
        
        //contiene la instruccion para actualizar
        String sql = "UPDATE Paciente SET nombre_paciente = ?, apellido_paciente = ?, carnet = ?, sintomas = ?, sexo = ?, telefono = ?, fecha_nacimiento = ?, direccion = ? WHERE id_paciente = ?";
        
        try(Connection conn = c.conectar()){
            
            if (conn == null){
                throw new SQLException("No se pudo conectar a la base de datos, favor revisar sus credenciales");
            }
            
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(2, paciente.getNombre_paciente());
                ps.setString(3, paciente.getApellido_paciente());
                ps.setString(4, paciente.getCarnet());
                ps.setString(5, paciente.getSintomas());
                ps.setString(6, paciente.getSexo());
                ps.setString(7, paciente.getTelefono());
                ps.setDate(8, java.sql.Date.valueOf(paciente.getFecha_nacimiento()));
                ps.setString(9, paciente.getDireccion());
                
                ps.executeUpdate();
            }
        }
        
        return false;
    }
    
    //metodo para eliminar un paciente
    public boolean eliminar(Paciente paciente) throws SQLException{
        
        //instruccion para borrar el paciente por medio del id
        String sql = "DELETE FROM Paciente WHERE id_paciente = ?";
        
        try(Connection conn = c.conectar()){
            
            if(conn == null){
                throw new SQLException("No se pudo conectar a la base de datos, favor revisar sus credenciales");
            }
            
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, paciente.getId_paciente());
                
                ps.executeUpdate();
            }
        }
        
        return false;
    }
    
    //metodo para consultar paciente
    public List<Paciente> consultar() throws SQLException{
        
        List<Paciente> lstPaciente = new ArrayList<>();
        
        String sql = "SELECT * FROM Paciente";
        
        try(Connection conn = c.conectar()){
            
            if(conn == null){
                throw new SQLException("No se pudo conectar a la base de datos, favor revisar sus credenciales");
            }
            
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                
                
                while(rs.next()){
                    int id_paciente = rs.getInt("id");
                    String nombre_paciente = rs.getString("Nombre");
                    String apellido_paciente = rs.getString("Apellido");
                    String carnet = rs.getString("Carnet");
                    String sintomas = rs.getString("Sintomas");
                    String sexo = rs.getString("Sexo");
                    java.sql.Date fecha_nacimiento = rs.getDate("Fecha nacimiento");
                    String telefono = rs.getString("Telefono");
                    String direccion = rs.getString("Direccion");
                    
                    lstPaciente.add(paciente);
                }
            }
        }
        
        return lstPaciente;
    }
}
