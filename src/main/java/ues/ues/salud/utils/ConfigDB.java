
package ues.ues.salud.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase ConfigDB : Encargada de Obtener y Asignar los valores del archivo config.properties 
 * @author Samuel
 */
public class ConfigDB {
    //Variables de la clase
    private static Properties properties = new Properties();
    private static String host; //Servidor
    private static String port; //Puerto
    private static String database; //Nombre de la base de datos
    private static String user; //Usuario de la base de datos
    private static String password; //Contraseña
    
    //Metodo Estatico para crear el archivo config.properties
    static {
        try{
            //Creación del archivo en la ruta especificada 
            FileInputStream input
                    = new FileInputStream("C:/UES-SALUD/config.properties");
            properties.load(input); //Cargamos el archivo
            
            //Asignamos valores por Default 
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            database = properties.getProperty("database");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    //Setters y Getters de las variables de clase 
    
    public static String getHost(){
            return properties.getProperty("host");
    }
    
    public static String getPort(){
            return properties.getProperty("port");
    }
    
    public static String getDB(){
            return properties.getProperty("database");
    }
    public static String getUser(){
            return properties.getProperty("user");
    }
    
    public static String getPassword(){
            return properties.getProperty("password");
    }
    
    public static void setHost(String host){
    properties.setProperty("host", host);
}

    public static void setPort(String port) {
        properties.setProperty("port", port);
    }

    public static void setDB(String db) {
        properties.setProperty("database", db);
    }

    public static void setUser(String user) {
        properties.setProperty("user", user);
    }

    public static void setPassword(String password) {
        properties.setProperty("password", password);
    }
    
    //Método para guardar nuevas credenciales en el archivo config.properties
    public static void guardarConfiguracion() throws FileNotFoundException, IOException{
        try (FileOutputStream fos
                = new FileOutputStream("C:/UES-SALUD/config.properties")) {

            properties.store(fos, "Configuracion actualizada");

        }
    }
}
