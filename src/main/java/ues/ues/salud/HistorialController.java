/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ues.ues.salud.Dao.PacienteDao;
import ues.ues.salud.Dao.TriajeDao;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.Triaje;

/**
 * FXML Controller class
 *
 * @author su487
 */
public class HistorialController implements Initializable {

    @FXML
    private Button btnBuscar;
    
    @FXML
    private VBox vBusqueda;
    
    @FXML
    private TextField txtCarnet;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTodos();
    }    
    
    
    @FXML
    private void Buscar(){
        System.out.println("presionado = ");
        PacienteDao paciDao = new PacienteDao();
        List<Paciente> pacientes = paciDao.buscarRegistro(txtCarnet.getText());
        pacientes.forEach(System.out::println);
        String[] cabeceras = {"ID","Carnet", "Nombres","Apellidos","Género", "Edad","Télefono","Dirección","Cantidad de Consultas"};
        String[] atributos = {"id_paciente","carnet", "nombre_paciente","apellido_paciente","sexo", "edad","telefono","direccion","consultas"};
        Parent tabla = cargarTabla("TablaInformacion.fxml", cabeceras, atributos, pacientes);
        if (vBusqueda.getChildren().size() > 1) {
            vBusqueda.getChildren().remove(2); // Quitamos la tabla general vieja
        }

        vBusqueda.getChildren().add(tabla);

    }
    
    
    public<T> Parent cargarTabla(String panel,String[] columnas,String[] atributos,List<T> generica ){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(panel));
        try {
            Parent root = loader.load();
            TablaInformacionController tabla = loader.getController();
            tabla.setColumns(columnas,atributos);
            
            tabla.setData(FXCollections.observableArrayList(generica));
            return root;
        } catch (IOException ex) {
            System.out.println("Error al cargar en"+panel + ex);
            ex.getMessage();
           
        }
        
        return null;
    }
    
    private void cargarTodos(){
        TriajeDao triaDao = new TriajeDao();
        List<Triaje> historial = triaDao.listarTodos(null,null);
        String[] cabeceras = {"Carnet", "Nombres","Apellidos", "Edad", "Género", "Especialidad","Sintomas","Temperatura","Presión Arterial","Urgencia"};
        String[] atributos = {"CarnetPaciente", "NombrePaciente","ApellidoPaciente", "EdadPaciente", "GeneroPaciente", 
            "NombreEspecialidad","Sintomas","Temperatura","PresionArterial","Nivel_urgencia"};
        Parent tabla = cargarTabla("TablaInformacion.fxml", cabeceras, atributos, historial);
        vBusqueda.getChildren().add(tabla);
    }
    
}
