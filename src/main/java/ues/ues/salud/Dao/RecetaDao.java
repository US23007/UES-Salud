/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Interface.DaoInterface;
import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.model.DetalleReceta;
import ues.ues.salud.model.Receta;
import ues.ues.salud.utils.DetalleRecetaTable;

/**
 *
 * @author su487
 */
public class RecetaDao implements DaoInterface<Receta>{

    @Override
    public boolean insertarRegistro(Receta entidad) {
       String sql = "{call sp_insertar_receta(?, ?, ?)}";
        
        try {
            Conexion con = new Conexion();
            
            CallableStatement cs = con.conectar().prepareCall(sql);
       
            cs.setInt(1, entidad.getTriaje().getId_triaje());
            cs.setString(2, entidad.getDoctor().getNombre().concat(" ").concat(entidad.getDoctor().getApellidos()));
            cs.setString(3, entidad.getDiagnostico()); 
            
           
            int filasAfectadas = cs.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al ejecutar SP: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarRegistro(Receta entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    @Override
    public List<Receta> listarTodos(String campo, String valor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int insertarRecetaYDetalles(int idTriaje, int idDoctor, String diagnostico, List<DetalleReceta> medicamentos) throws SQLException{
        Conexion con = new Conexion();
        Connection conectar = con.conectar();
        String queryReceta = "INSERT INTO recetas (id_triaje, id_doctor, diagnostico) VALUES (?, ?, ?)";
        String queryDetalle = "INSERT INTO detalles_recetas (id_receta, nombre_medicamento, dosis, indicaciones) VALUES (?, ?, ?, ?)";
        
        try {
            // Desactivamos el autocommit para manejarlo como una sola transacción atómica
            conectar.setAutoCommit(false);

            // 1. Insertar la Cabecera de la Receta
            PreparedStatement psReceta = conectar.prepareStatement(queryReceta, Statement.RETURN_GENERATED_KEYS);
            psReceta.setInt(1, idTriaje);
            psReceta.setInt(2, idDoctor);
            psReceta.setString(3, diagnostico);
            psReceta.executeUpdate();

            // Rescatamos el id_receta autoincrementable que se acaba de generar
            ResultSet rsKeys = psReceta.getGeneratedKeys();
            int idRecetaGenerado = 0;
            if (rsKeys.next()) {
                idRecetaGenerado = rsKeys.getInt(1);
            }

            // 2. Insertar los Medicamentos usando Batch (en lote) para mayor velocidad
            if (idRecetaGenerado > 0 && medicamentos != null && !medicamentos.isEmpty()) {
                PreparedStatement psDetalle = conectar.prepareStatement(queryDetalle);
                for (DetalleReceta med : medicamentos) {
                    psDetalle.setInt(1, idRecetaGenerado);
                    psDetalle.setString(2, med.getNombre_medicamento());
                    psDetalle.setString(3, med.getDosis());
                    psDetalle.setString(4, med.getIndicaciones());
                    psDetalle.addBatch(); // Se acumula en memoria
                }
                psDetalle.executeBatch(); // Se envían todos de un solo golpe a MySQL
            }

            // Si todo llegó hasta aquí sin caer al catch, consolidamos los cambios
            conectar.commit();
            return idRecetaGenerado; // Retornamos el ID por si te sirve para el XML

        } catch (Exception e) {
            System.out.println("Error al guardar la receta y sus detalles: " + e.getMessage());
            try {
               if (conectar != null && !conectar.getAutoCommit()) {
                conectar.rollback(); 
            }
            } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return 0; // Significa que falló
        } finally {
            try { if (con.conectar() != null) con.conectar().close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @Override
    public Receta buscarRegistro(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
