
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

/*
Fecha : 1/06/2026
Clase Conexion : Es uno de los ejes fundamentales  de nuestro sistemas ya que es el mensajero e interprete entre el programa y la db
Autores : US23007 y LM25002
*/


public class Conexion {
    private static Connection con; //Variable del tipp sql Connection
    
    
   
    //Método para obtener la conexion para ser  usado en el login
    public boolean getConexion(String host,String port,String db,String user,String pass) throws SQLException{ 
        //Cadena de Conexion para el Driver Manager 
        String URL = "jdbc:mysql://" //JDBC de MySql
            +host //Servidor
            +":"
            +port //Puerto
            +"/"
            +db; //Base de Datos que se desea conectar
        
        System.out.println("URL = " + URL);
        try{
            // Uso de Driver Connection 
            con = DriverManager.getConnection(
                    URL, // Cadena de conexion
                    user, //Nombre de Usuario para acceder a la base de datos
                    pass //Contraseña
            
            );
            
            
            return true; //Retorna Verdadero si encuentra los datos en mysql
                    
        }catch(SQLException ex){
            System.out.println("ex = " + ex); //Caso de error 
            //Mensaje de Error 
            Notifications.create()
                    .title("Acceso Invalido")
                    .text("Las credenciales ingresadas son incorrectas")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece  abajo a la derecha
                    .showError();
        }finally{
            con.close(); //Cierra la conexion
        }
         
        return false; //No puede acceder a la aplicacion 
    }
    
    
    //Funcion conectar Retorna una variable del tipo Connection es nuestro mensajero que llevara y traera la informacion entre la db y el programa
    public Connection conectar() throws SQLException{
        String URL = "jdbc:mysql://"
                //Una vez obtenido los datos se pasan a guardar en un archivo config.properties para no estar escribiendo las credenciales cada vez que se inicia sesion
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
            
            return con; //Ingresa al Sistema
                    
        }catch(SQLException ex){
            System.out.println("ex = " + ex);
        }
        
        return null; //Retorna Vacio al no encontrar las credenciales o db no existe 
    }
    
    
    
    
    
    
   
    
}
