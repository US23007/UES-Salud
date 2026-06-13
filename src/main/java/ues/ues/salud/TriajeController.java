/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.PacienteCritico;
import ues.ues.salud.model.PacienteEstable;
import ues.ues.salud.model.PacienteMedio;
import ues.ues.salud.utils.ExpedienteXML;

/**
 * FXML Controller class
 *
 * @author su487
 */
public class TriajeController implements Initializable {

    private Paciente pacienteGlobal = null;
     @FXML
    private Tab tInfo;
    
     @FXML
    private Tab tDatos;
     @FXML
     private TabPane tabPane;
    
    @FXML
    private Button btnContinuar;
    
    @FXML 
    private ComboBox cbxUrgencia;
    
    @FXML
    private TitledPane tabNivel;
    
    @FXML
    private TextField txtCarnet;
    
    @FXML
    private TextField txtNombres;
            
            
    @FXML
    private TextField txtApellidos;
            
    @FXML
    private DatePicker dtFechaNacimiento;
    
    @FXML
    private RadioButton cbxHombre;
    
     @FXML
    private RadioButton cbxMujer;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TextArea  txtDireccion;
    
    @FXML 
    private ChoiceBox cbxEspecialidad;
    
    @FXML
    private TextArea txtSintomas;
    
    
    
    
    @FXML
    private Button btnAtras;
    
    @FXML
    private Button btnGuardar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxUrgencia.getItems().add("BAJA");
        cbxUrgencia.getItems().add("MEDIA");
        cbxUrgencia.getItems().add("ALTA");
        
        cbxEspecialidad.getItems().add("Medica");
    }    
    
    
    @FXML
    private void Siguiente(){
        pacienteGlobal = new Paciente();
        pacienteGlobal.setNombre_paciente(txtNombres.getText());
        pacienteGlobal.setApellido_paciente(txtApellidos.getText());
        pacienteGlobal.setCarnet(txtCarnet.getText());
        
        if(cbxHombre.isSelected()){
            cbxMujer.setSelected(false);
            pacienteGlobal.setSexo(cbxHombre.getText());
        }else if(cbxMujer.isSelected()){
            cbxHombre.setSelected(false);
            pacienteGlobal.setSexo(cbxMujer.getText());
        }else{
            System.out.println("NO HA SELECCIONADO NADA");
        }
        
        pacienteGlobal.setFecha_nacimiento(dtFechaNacimiento.getValue());
        
        pacienteGlobal.setTelefono(txtTelefono.getText());
        
        pacienteGlobal.setDireccion(txtDireccion.getText());
        
        tInfo.setDisable(false);
        tabPane.getSelectionModel().select(tInfo);
        tDatos.setDisable(true);
        
        
    }
    
    
    @FXML
    private void cambiarColor(){
        tabNivel.getStyleClass().removeAll(
                "urgencia-baja",
                "urgencia-media",
                "urgencia-alta"
        );
        
        switch (cbxUrgencia.getValue().toString()) {
            case "BAJA":
                tabNivel.getStyleClass().add("urgencia-baja");
                break;
            case "MEDIA":
                tabNivel.getStyleClass().add("urgencia-media");
                break;
            case "ALTA":
                tabNivel.getStyleClass().add("urgencia-alta");
                break;
            default:
                throw new AssertionError();
        }
    }
    
    
    @FXML
    private void Retroceder(){
         
        txtNombres.setText(pacienteGlobal.getNombre_paciente());
        txtApellidos.setText(pacienteGlobal.getApellido_paciente());
        txtCarnet.setText(pacienteGlobal.getCarnet());
        
        if(pacienteGlobal.getSexo().equalsIgnoreCase("Hombre")){
            cbxHombre.setSelected(true);
            cbxMujer.setSelected(false);
        }else if(pacienteGlobal.getSexo().equalsIgnoreCase("Mujer")){
            cbxHombre.setSelected(false);
            cbxMujer.setSelected(true);
        }
        
        dtFechaNacimiento.setValue(pacienteGlobal.getFecha_nacimiento());
        
        txtTelefono.setText(pacienteGlobal.getTelefono());
        
        
        txtDireccion.setText(pacienteGlobal.getDireccion());
        
        tInfo.setDisable(true);
        tabPane.getSelectionModel().select(tDatos);
        tDatos.setDisable(false);
    }
    
    @FXML
    private void Guardar() throws ParserConfigurationException, TransformerException{
        String urgencia = cbxUrgencia.getValue().toString();
        System.out.println("P = " + pacienteGlobal.toString());
        System.out.println("urgencia = " + urgencia);
        System.out.println("sintomas = " + txtSintomas.getText());
        if(urgencia.equalsIgnoreCase("ALTA")){
            PacienteCritico critico = new PacienteCritico(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(), 
                    txtSintomas.getText(),pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());
                    
            System.out.println("CRITICO = " +critico.toString() );
                     ExpedienteXML.generarExpedienteXML(critico, cbxEspecialidad.getValue().toString(), cbxUrgencia.getValue().toString());
                    
                    
         }else if(urgencia.equalsIgnoreCase("MEDIA")){
            PacienteMedio medio = new PacienteMedio(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(), 
                    txtSintomas.getText(),pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());
                    
            System.out.println("MEDIA = " +medio.toString() );
                     ExpedienteXML.generarExpedienteXML(medio, cbxEspecialidad.getValue().toString(), cbxUrgencia.getValue().toString());
         }else if(urgencia.equalsIgnoreCase("BAJO")){
             PacienteEstable estable = new PacienteEstable(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(), 
                    txtSintomas.getText(),pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());
                    
             System.out.println("BAJA = " +estable.toString() );
                     ExpedienteXML.generarExpedienteXML(estable, cbxEspecialidad.getValue().toString(), cbxUrgencia.getValue().toString());
             
         }
        
       
    }
}
