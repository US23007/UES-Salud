package ues.ues.salud;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.utils.ExpedienteXML;


public class PrimaryController implements Initializable{

    @FXML
    private Button btnRegistro;
    
    @FXML
    private Button btnBuscar;
    
    @FXML
    private BorderPane bPrincipal;
    
     @FXML
    private MenuItem mAbrir;
     
     @FXML
    private MenuItem mConsultas;
     
     @FXML
    private MenuItem mNuevo;
   
    @FXML
    private Button btnHistorial;
    
    @FXML
    private Button btnEstadisticas;
    
    @FXML Button btnReportes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void Registro(){
        cargarPanel("triaje.fxml",null,true,false);
    }
    
    @FXML
    private void Buscar(){
        cargar("busqueda.fxml");
    }
    
    @FXML
    private void Historial(){
        cargar("Historial.fxml");
    }
    
    @FXML
    private void Estadisticas(){
        cargar("Reporte.fxml");
    }
    public void cargarPanel(String panel,Paciente paciente,boolean Guardar,boolean Modificar){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(panel));
        try {
            Parent root = loader.load();
            TriajeController triaje = loader.getController();
            triaje.setData(paciente,Guardar,Modificar);
            bPrincipal.setCenter(root);
        } catch (IOException ex) {
            System.out.println("Error al cargar en"+panel + ex);
            ex.getMessage();
        }
    }
    
    public void cargar(String panel){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(panel));
        try {
            Parent root = loader.load();
            bPrincipal.setCenter(root);
        } catch (IOException ex) {
            System.out.println("Error al cargar en"+panel + ex);
            ex.getMessage();
        }
    }
    
    @FXML
    private void Abrir(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos XML (*.xml)", "*.xml");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Seleccionar Expediente Clínico XML");
       
        File carpetaExpedientes = new File("C:/UES-SALUD/expedientes");

        if (carpetaExpedientes.exists() && carpetaExpedientes.isDirectory()) {
            chooser.setInitialDirectory(carpetaExpedientes);
        } else {

            carpetaExpedientes.mkdirs();
            chooser.setInitialDirectory(carpetaExpedientes);
        }
        
        File archivoSeleccionado = chooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            try {
                // 1. Invocar el método lector que creamos pasándole el archivo real
                Paciente pacienteCargado = ExpedienteXML.cargarExpedienteXML(archivoSeleccionado);

                // 2. Mapear u organizar los datos recuperados de vuelta en los campos de JavaFX
                cargarPanel("triaje.fxml",pacienteCargado,false,true);
                
                Notifications.create()
                        .title("Proceso Completado")
                        .text("Expediente Cargado Correctamente")
                        .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                        .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                        .showInformation();
               

                

            } catch (Exception e) {
                // Por si el archivo XML está corrupto, mal editado o no cumple con las etiquetas esperadas
                System.out.println("Algo salio mal en PrimaryController en Abrir() = " +e);
                e.printStackTrace();
                e.getMessage();
                
            }
        } else {
            System.out.println("Carga de archivo cancelada por el usuario.");
        }
    }
    
    
    @FXML
    private void Reportes(){
        cargar("Estadisticas.fxml");
    }
    
    @FXML
    public void Consultas() throws IOException {
        File carpetaExpedientes = new File("C:/UES-SALUD/consultas");
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(carpetaExpedientes);
        }
    }
    
    @FXML
    public void Salir() throws IOException{
        App.setRoot("login");
    }
}
