/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Samuel
 */
public class ConfigDB {
    private static Properties properties = new Properties();
    private static String host;
    private static String port;
    private static String database;
    private static String user;
    private static String password;
    static {
        try{
            FileInputStream input
                    = new FileInputStream("C:/UES-SALUD/config.properties");
            properties.load(input);
            
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            database = properties.getProperty("database");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
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
    
    public static void guardarConfiguracion() throws FileNotFoundException, IOException{
        try (FileOutputStream fos
                = new FileOutputStream("C:/UES-SALUD/config.properties")) {

            properties.store(fos, "Configuracion actualizada");

        }
    }
}
