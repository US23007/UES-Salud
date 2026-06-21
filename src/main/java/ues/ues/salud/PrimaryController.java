/**
 * Controlador principal de la interfaz del sistema UES-Salud.
 *
 * Funciones principales:
 * - Administrar la navegación entre módulos.
 * - Cargar las vistas del sistema.
 * - Gestionar el menú lateral.
 * - Abrir expedientes y reportes generados.
 *
 * Elaborado y documentado por:
 * Astrid Escobar PE25005 APE115
 */

package ues.ues.salud;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    
    List<Button> botones = new ArrayList<>();
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
    
    // Inicializa la lista de botones del menú principal
    // para controlar el estilo visual del módulo activo.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       botones.add(btnRegistro);
       botones.add(btnBuscar);
       botones.add(btnHistorial);
       botones.add(btnEstadisticas);
       botones.add(btnReportes);
       
    }
    
    public void clearStyle(Button b, String cssClass){
        b.getStyleClass().remove(cssClass);
    }
    
    public void addStyle(Button b, String cssClass){
        b.getStyleClass().add(cssClass);
    }
    // Cambia el estilo visual del botón seleccionado
    // para indicar al usuario el módulo actual.
    private void cambiarBotonActivo(Button botonSeleccionado) {
        botones.forEach(bt -> {
            bt.getStyleClass().removeAll("menu-button", "menu-button-active");
            if (bt == botonSeleccionado) {
                clearStyle(bt, "menu-button");
                addStyle(bt, "menu-button-active");
            } else {
                clearStyle(bt, "menu-button-active");
                addStyle(bt, "menu-button");
            }
        });
    }
    // Abre el módulo de registro y triaje de pacientes.
    @FXML
    private void Registro(){
        cambiarBotonActivo(btnRegistro);
        cargarPanel("triaje.fxml",null,true,false);
        // Carga dinámicamente una vista FXML dentro del panel principal de la aplicación.
    }
    
    @FXML
    private void Buscar(){
        cambiarBotonActivo(btnBuscar);
        cargar("busqueda.fxml");
    }
    
    @FXML
    private void Historial(){
        cambiarBotonActivo(btnHistorial);
        cargar("Historial.fxml");
    }
    // Abre la pantalla de reportes y estadísticas.
    @FXML
    private void Estadisticas(){
        cambiarBotonActivo(btnEstadisticas);
        cargar("Reporte.fxml");
    }
    
    // Carga una vista FXML con el expediente del paciente dentro del área principal.
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
    // Carga una vista FXML simple dentro del área principal.
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
   // Permite seleccionar y cargar un expediente clínico almacenado en formato XML.
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
                // Invocar el método lector que creamos pasándole el archivo real
                Paciente pacienteCargado = ExpedienteXML.cargarExpedienteXML(archivoSeleccionado);

                // Mapear u organizar los datos recuperados de vuelta en los campos de JavaFX
                cargarPanel("triaje.fxml",pacienteCargado,false,true);
                
                Notifications.create()
                        .title("Proceso Completado")
                        .text("Expediente Cargado Correctamente")
                        .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                        .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                        .showInformation();
               

                cambiarBotonActivo(btnRegistro);

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
    
    //Abre la vista de Estadisticas
    @FXML
    private void Reportes(){
        cambiarBotonActivo(btnReportes);
        cargar("Estadisticas.fxml");
    }
    
    
    //Abre la ruta donde se guardan las consultas de los pacientes
    @FXML
    public void Consultas() throws IOException {
        File carpetaExpedientes = new File("C:/UES-SALUD/consultas");
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(carpetaExpedientes);
        }
    }
    // Cierra la sesión actual y regresa al login.
    @FXML
    public void Salir() throws IOException{
        App.setRoot("login");
    }
    // Abre la carpeta donde se guardan los reportes. 
    @FXML 
    private void ver_Reportes() throws IOException{
        File carpetaExpedientes = new File("C:/UES-SALUD/reportes");
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(carpetaExpedientes);
        }
    }
    
}
