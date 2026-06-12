package Conexion;

/**
 *
 * @author Daniel Lopez LM25002
 */

//importacion de libreria necesaria para conectar con la base de datos
import java.sql.*;
public class ConexionBD {
    
    //variables necesarias para crear la conexion
    String url = "jdbc:mysql://localhost:3306/tienda";
    String usuario = "root";
    String contraseña = "admin123";
    
    //Metodo Constructor
    public ConexionBD(){
        //manejo de errores en la conexion
        try{
            //Establecemos la conexion
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            
            System.out.println("Conexion exitosa");
            
            //cerrar recursos
            conexion.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
