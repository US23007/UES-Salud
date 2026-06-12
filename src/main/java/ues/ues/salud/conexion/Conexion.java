
package ues.ues.salud.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ues.ues.salud.utils.ConfigDB;



public class Conexion {
    private static Connection con;
    
    
   
    public boolean getConexion(String host,String port,String db,String user,String pass) throws SQLException{
        String URL = "jdbc:mysql://"
            +host
            +":"
            +port
            +"/"
            +db;
        
        System.out.println("URL = " + URL);
        try{
            
            con = DriverManager.getConnection(
                    URL,
                    user,
                    pass
            
            );
            
            
            return true;
                    
        }catch(SQLException ex){
            System.out.println("ex = " + ex);
            Notifications.create()
                    .title("Acceso Invalido")
                    .text("Las credenciales ingresadas son incorrectas")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showError();
        }finally{
            con.close();
        }
        
        return false;
    }
    
    
    public Connection conectar() throws SQLException{
        String URL = "jdbc:mysql://"
            +ConfigDB.getHost()
            +":"
            +ConfigDB.getPort()
            +"/"
            +ConfigDB.getDB();
        
        System.out.println("URL = " + URL);
        try{
            
            con = DriverManager.getConnection(
                    URL,
                    ConfigDB.getUser(),
                    ConfigDB.getPassword()
            
            );
            
            System.out.println("Usuario = " +ConfigDB.getUser());
            System.out.println("Pass = " +ConfigDB.getPassword());
            return con;
                    
        }catch(SQLException ex){
            System.out.println("ex = " + ex);
        }
        
        return null;
    }
    
    
    
    
    
    
   
    
}
