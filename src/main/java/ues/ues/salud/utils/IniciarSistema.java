
package ues.ues.salud.utils;



import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Clase IniciarSistema : Hemos definido esta clase por integridad y orden de la aplicacion , aqui se crearan carpetas para el almacenamiento y ubicacion de archivos (pdf o xml) de reportes, consultas y expedientes 
 *  Fecha : 30/05/2026
 * @author US23007 Samuel De Jesus Umaña Sorto
 */
public class IniciarSistema {
     public static void inicializarSistema(){

        try{

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD") //Crear carpeta UES-Salud en Disco Local
            );

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/consultas") //Crear Carpeta Consultas
            );

            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/reportes") //Crear Carpeta Reportes
            );
            
            Files.createDirectories(
                    Paths.get("C:/UES-SALUD/expedientes") //Crear Carpeta Expedientes
            );
 
            Path config = Paths.get("C:/UES-SALUD/config.properties"); //Crear el archivo config.properties con valores por default 
            if (!Files.exists(config)) {

                Properties props = new Properties();

                props.setProperty("host", "localhost"); //Servidor
                props.setProperty("port", "3306"); //Puerto
                props.setProperty("database", "ues_salud"); //Nombre de la base de datos
                props.setProperty("user", "root");//Usuario 
                props.setProperty("password", "admin123"); //Contraseña

                try (OutputStream out = Files.newOutputStream(config)) {
                    props.store(out, "Configuracion UES-SALUD"); //Crear archivo y notificar por actualizaciones 
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
