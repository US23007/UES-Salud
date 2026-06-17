/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.utils;



import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 *
 * @author Samuel
 */
public class IniciarSistema {
     public static void inicializarSistema(){

        try{

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD")
            );

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/consultas")
            );

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/reportes")
            );
            
            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/expedientes")
            );
 
            Path config = Paths.get("C:/UES-SALUD/config.properties");
            if (!Files.exists(config)) {

                Properties props = new Properties();

                props.setProperty("host", "localhost");
                props.setProperty("port", "3306");
                props.setProperty("database", "ues_salud");
                props.setProperty("user", "root");
                props.setProperty("password", "admin123");

                try (OutputStream out = Files.newOutputStream(config)) {
                    props.store(out, "Configuracion UES-SALUD");
                }
            }

            System.out.println(
                    "Sistema inicializado"
            );
            
            
            
            

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}
