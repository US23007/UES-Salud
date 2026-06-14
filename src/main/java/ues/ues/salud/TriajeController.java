/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Dao.PacienteDao;
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
    
    @FXML
    private Button btnModificar;
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxUrgencia.getItems().add("BAJA");
        cbxUrgencia.getItems().add("MEDIA");
        cbxUrgencia.getItems().add("ALTA");
        
        cbxEspecialidad.getItems().add("Medica");
    }    
    
    public void setData(String carnet,String nombre,String apellidos,String telefono,String direccion,String sexo,LocalDateTime fecha,boolean Guardar,boolean Modificar){
        txtCarnet.setText(carnet);
        txtNombres.setText(nombre);
        txtApellidos.setText(apellidos);
        txtTelefono.setText(telefono);
        txtDireccion.setText(direccion);
        
        if(sexo.equalsIgnoreCase("HOMBRE")){
            cbxHombre.setSelected(true);
        }else if(sexo.equalsIgnoreCase("MUJER")){
            cbxMujer.setSelected(true);
        }
        
        if(fecha == null){
            dtFechaNacimiento.setValue(null);
        }else{
            dtFechaNacimiento.setValue(fecha.toLocalDate());
        }
        
        btnGuardar.setVisible(Guardar);
        this.btnModificar.setVisible(Modificar);
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
        
        System.out.println("PACIENTE FECHA UI= " +dtFechaNacimiento.getValue());
        
        dtFechaNacimiento.getChronology().date(dtFechaNacimiento.getConverter().fromString(dtFechaNacimiento.getEditor().getText()));
        LocalDateTime fecha = dtFechaNacimiento.getValue().atStartOfDay();
        System.out.println("PACIENTE FECHA = " +fecha);
        pacienteGlobal.setFecha_nacimiento(fecha);
        
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
               tabNivel.getStyleClass().add("titlePane");
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
        
        dtFechaNacimiento.setValue(pacienteGlobal.getFecha_nacimiento().toLocalDate());
        
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
        PacienteDao pacienDao = new PacienteDao();
        
        if(urgencia.equalsIgnoreCase("ALTA")){
            PacienteCritico critico = new PacienteCritico(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo() 
                    ,pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());
                   if(pacienDao.insertarRegistro(critico)){
                       ExpedienteXML.generarExpedienteXML(critico,txtSintomas.getText(),cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                       limpiarCampos();
                   }
            
            System.out.println("CRITICO = " +critico.toString() );
                     

                    
         }else if(urgencia.equalsIgnoreCase("MEDIA")){
            PacienteMedio medio = new PacienteMedio(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                     pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

            if (pacienDao.insertarRegistro(medio)) {
                 ExpedienteXML.generarExpedienteXML(medio,txtSintomas.getText(), cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                limpiarCampos();
            }
            
            
                    
         }else if(urgencia.equalsIgnoreCase("BAJA")){
             PacienteEstable estable = new PacienteEstable(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                     pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

            if (pacienDao.insertarRegistro(estable)) {
                ExpedienteXML.generarExpedienteXML(estable,txtSintomas.getText(),cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                limpiarCampos();
            }
                     
             
         }
        
       
    }
    
    
    @FXML
    private void Modificar() throws ParserConfigurationException, TransformerException{
        String urgencia = cbxUrgencia.getValue().toString();
        PacienteDao pacienteDao = new PacienteDao();
        
        if(urgencia.equalsIgnoreCase("ALTA")){
            PacienteCritico critico = new PacienteCritico(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo() 
                    ,pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());
                   if(pacienteDao.modificarRegistro(critico)){
                       ExpedienteXML.generarExpedienteXML(critico,txtSintomas.getText(),cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                       limpiarCampos();
                   }
            
            System.out.println("CRITICO = " +critico.toString() );
                     

                    
         }else if(urgencia.equalsIgnoreCase("MEDIA")){
            PacienteMedio medio = new PacienteMedio(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                     pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

            if (pacienteDao.modificarRegistro(medio)) {
                 ExpedienteXML.generarExpedienteXML(medio,txtSintomas.getText(), cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                limpiarCampos();
            }
            
            
                    
         }else if(urgencia.equalsIgnoreCase("BAJA")){
             PacienteEstable estable = new PacienteEstable(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                     pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

            if (pacienteDao.modificarRegistro(estable)) {
                ExpedienteXML.generarExpedienteXML(estable,txtSintomas.getText(),cbxEspecialidad.getSelectionModel().getSelectedItem().toString(), cbxUrgencia.getSelectionModel().getSelectedItem().toString());
                limpiarCampos();
            }
                     
             
         }
        
       
    }

    private void limpiarCampos() {
        txtCarnet.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        dtFechaNacimiento.setValue(null);
        cbxHombre.setSelected(false);
        cbxMujer.setSelected(false);
        txtTelefono.setText("");
        cbxEspecialidad.setValue(null);
        cbxUrgencia.setValue(null);
        txtDireccion.setText("");
        txtSintomas.setText("");
        tInfo.setDisable(true);
        tabPane.getSelectionModel().select(tDatos);
        tDatos.setDisable(false);
    }
    
    
}
