/**
 * Controlador del módulo de búsqueda de pacientes.
 * Permite consultar registros, realizar búsquedas dinámicas
 * por carnet y gestionar la desactivación de pacientes.
 * Autor: Astrid Escobar PE25005 APE115
 */
package ues.ues.salud;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ues.ues.salud.Dao.PacienteDao;
import ues.ues.salud.model.Paciente;

/**
 * 
 * Clase Busqueda Controller 
 * @author  Astrid Escobar PE25005 APE115
 * Fecha : 05/06/2026
 */

// Método ejecutado al cargar la ventana.
// Inicializa la tabla de pacientes y configura la búsqueda dinámica.
public class BusquedaController implements Initializable {

    @FXML
    private Button btnBuscar;
    
    @FXML
    private VBox vBusqueda;
    
    @FXML
    private TextField txtCarnet;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cargar Todos los registros al iniciar la ventana
        cargarTodos();

        
        btnBuscar.setVisible(false);
        btnBuscar.setManaged(false);
        
        //Evento para cambiar entre tablas 
        txtCarnet.textProperty().addListener((obs, oldValue, newValue) -> {
            
            if(newValue.trim().isEmpty()){

                if(vBusqueda.getChildren().size() > 1){
                    vBusqueda.getChildren().remove(2);
                }

                cargarTodos();
            }else{
                Buscar();
            }
        });
        
        
    }    
    
    // Realiza una búsqueda de pacientes utilizando el carnet ingresado.
    // Los resultados encontrados se muestran en una tabla.
    @FXML
    private void Buscar(){
        PacienteDao paciDao = new PacienteDao();

        List<Paciente> pacientes =
                paciDao.buscarPorCarnetParcial(txtCarnet.getText().trim());

        String[] cabeceras = {
            "Carnet",
            "Nombre Completo",
            "Edad",
            "Género",
            "Cantidad de Consultas"
        };

        String[] atributos = {
            "carnet",
            "nombre_paciente",
            "edad",
            "sexo",
            "consultas"
        };

        Parent tabla = cargarTabla(
                "TablaInformacion.fxml",
                cabeceras,
                atributos,
               pacientes,
               paciente -> {
                    
                    confirmarYEliminar(paciente);
                }
        );
        
        

        if (vBusqueda.getChildren().size() > 1) {
            vBusqueda.getChildren().remove(2);
        }

        vBusqueda.getChildren().add(tabla);

    }
    
    // Carga dinámicamente una tabla reutilizable y muestra los datos
    // recibidos desde la consulta realizada.
    public<T> Parent cargarTabla(String panel,String[] columnas,String[] atributos,List<T> generica,java.util.function.Consumer<T> accionEliminar ){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(panel));
        try {
            Parent root = loader.load();
            
            TablaInformacionController<T> tabla = loader.getController();
            tabla.setColumns(columnas,atributos);
            
            tabla.setData(FXCollections.observableArrayList(generica));
            if(accionEliminar != null){
                tabla.configurarMenuEliminar(accionEliminar);
            }
            return root;
        } catch (IOException ex) {
            System.out.println("Error al cargar en"+panel + ex);
            ex.getMessage();
           
        }
        
        return null;
    }
    // Carga todos los pacientes activos registrados en el sistema.
    private void cargarTodos(){
        PacienteDao paciDao = new PacienteDao();
        List<Paciente> pacientes = paciDao.listarTodos(null,null);
        String[] cabeceras = {"Carnet", "Nombre Completo", "Edad", "Género", "Cantidad de Consultas"};
        String[] atributos = {"carnet", "nombre_paciente", "edad", "sexo", "consultas"};
        Parent tabla = cargarTabla("TablaInformacion.fxml", cabeceras, atributos, pacientes,null);
        vBusqueda.getChildren().add(tabla);
    }

    // Solicita confirmación al usuario antes de desactivar un paciente (Popup Eliminar).
    // Si el usuario acepta, se actualiza el estado del registro.
    private void confirmarYEliminar(Paciente paciente) {
        PacienteDao pacienteDao = new PacienteDao();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Acción");
        alert.setHeaderText("¿Desea eliminar al paciente?");
        alert.setContentText("El estudiante con carnet " + paciente.getCarnet()
                + " (" + paciente.getNombre_paciente() + ") cambiará a estado inactivo.");

        
        ButtonType btnSi = new ButtonType("Sí, dar de baja");
        ButtonType btnNo = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnSi, btnNo);

        
        alert.showAndWait().ifPresent(respuesta -> {
            if (respuesta == btnSi) {

               
                boolean exito = pacienteDao.eliminarRegistro(paciente.getCarnet());

                if (exito) {
                 
                    Alert exitoAlert = new Alert(Alert.AlertType.INFORMATION);
                    exitoAlert.setTitle("Registro Actualizado");
                    exitoAlert.setHeaderText(null);
                    exitoAlert.setContentText("El expediente ha sido desactivado correctamente.");
                    exitoAlert.show();
                    
                   
                } else {
                   
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error de Conexión");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No se pudo actualizar el estado del paciente en la base de datos.");
                    errorAlert.show();
                }
            }
        });

    }
    
}
