/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import ues.ues.salud.Dao.EspecialidadDao;
import ues.ues.salud.Dao.PacienteDao;
import ues.ues.salud.Dao.TriajeDao;
import ues.ues.salud.model.Especialidad;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.PacienteCritico;
import ues.ues.salud.model.PacienteEstable;
import ues.ues.salud.model.PacienteMedio;
import ues.ues.salud.model.Triaje;
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
    private TextField txtPresion;
    
    @FXML
    private TextField txtTemperatura;
    
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
        EspecialidadDao espeDao = new EspecialidadDao();
        List<Especialidad> especialidades = espeDao.listarTodos(null,null);
        
        especialidades.forEach(e->{
            cbxEspecialidad.getItems().add(e.getNombreEspecialidad());
        
        });
    }    
    
    public void setData(Paciente paciente, boolean Guardar, boolean Modificar) {
        if (paciente == null) {
            txtTemperatura.setText("");
            txtPresion.setText("");
            txtSintomas.setText("");
            cbxEspecialidad.getSelectionModel().clearSelection();
            cbxUrgencia.getSelectionModel().clearSelection();
            dtFechaNacimiento.setValue(null);
            txtPresion.setText("");
        } else {

            Triaje ultimoTriaje = paciente.getUltimoTriaje();

            if (ultimoTriaje != null) {

                txtTemperatura.setText(String.valueOf(ultimoTriaje.getTemperatura()));
                txtPresion.setText(ultimoTriaje.getPresionArterial());
                txtSintomas.setText(ultimoTriaje.getSintomas());

                cbxUrgencia.setValue(ultimoTriaje.getNivel_urgencia());

                if (ultimoTriaje.obtenerEspecialida() != null) {
                    cbxEspecialidad.setValue(ultimoTriaje.obtenerEspecialida().getNombreEspecialidad());
                }
            } else {

                txtTemperatura.setText("");
                txtPresion.setText("");
                txtSintomas.setText("");
                cbxEspecialidad.getSelectionModel().clearSelection();
                cbxUrgencia.getSelectionModel().clearSelection();
            }
            txtCarnet.setText(paciente.getCarnet());
            txtNombres.setText(paciente.getNombre_paciente());
            txtApellidos.setText(paciente.getApellido_paciente());
            txtDireccion.setText(paciente.getDireccion());
            txtTelefono.setText(paciente.getTelefono());
            if(paciente.getSexo().equalsIgnoreCase("HOMBRE")){
                cbxHombre.setSelected(true);
            }else if(paciente.getSexo().equalsIgnoreCase("MUJER")){
                cbxMujer.setSelected(true);
            }else{
                cbxHombre.setSelected(false);
                cbxMujer.setSelected(false);
            }
            
            dtFechaNacimiento.setValue(paciente.getFecha_nacimiento().toLocalDate());
            cambiarColor();
        }
        btnGuardar.setVisible(Guardar);
        this.btnModificar.setVisible(Modificar);
        
        tInfo.setDisable(true);
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
        
        switch (cbxUrgencia.getSelectionModel().getSelectedItem().toString()) {
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
        
    String nombreEspecialidad = cbxEspecialidad.getSelectionModel().getSelectedItem().toString();
    String sintomas = txtSintomas.getText();
        PacienteDao pacienDao = new PacienteDao();
        Paciente pacienteInstanciado = null;
        TriajeDao triajeDao = new TriajeDao();
        if (urgencia.equalsIgnoreCase("ALTA")) {
            pacienteInstanciado = new PacienteCritico(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                     pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        } else if (urgencia.equalsIgnoreCase("MEDIA")) {
            pacienteInstanciado = new PacienteMedio(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                    pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        } else if (urgencia.equalsIgnoreCase("BAJA")) {
            pacienteInstanciado = new PacienteEstable(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                    pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        }
        
        if (pacienteInstanciado != null) {
            // 3. Crear los objetos de soporte con el nuevo diseño limpio de clases
            Especialidad especialidad = new Especialidad();
            especialidad.setNombreEspecialidad(nombreEspecialidad);

            Triaje triaje = new Triaje();
            triaje.setSintomas(sintomas);
            triaje.setNivel_urgencia(urgencia);

            // Asignamos las lecturas físicas que te hacían falta en pantalla (Temperatura y Presión)
            // Reemplaza 'txtTemperatura' y 'txtPresion' por los nombres reales de tus TextField
            try {
                triaje.setTemperatura(Double.parseDouble(txtTemperatura.getText()));
            } catch (NumberFormatException e) {
                triaje.setTemperatura(36.5); // Valor por defecto seguro si el campo está vacío o erróneo
            }
            triaje.setPresionArterial(txtPresion.getText());

            // Establecer las relaciones bidireccionales en memoria
            triaje.setPaciente(pacienteInstanciado);
            triaje.agregarEspecialidad(especialidad);
            pacienteInstanciado.agregarTriaje(triaje);

            // 4. PERSISTENCIA EN LA BASE DE DATOS
            // Primero aseguramos que el paciente exista o se registre
            boolean pacienteGuardado = pacienDao.insertarRegistro(pacienteInstanciado);

            // Segundo: Invocamos el procedimiento almacenado sp_insertar_triaje a través del DAO
            // Pasamos el objeto triaje completo que contiene las relaciones internas
            boolean triajeGuardado = triajeDao.insertarRegistro(triaje);

            // 5. Si ambos se guardaron en la BD, generamos el XML final de UES-Salud
            if (pacienteGuardado && triajeGuardado) {

                // Usamos tu método limpio de generación de XML
                ExpedienteXML.generarExpedienteXML(pacienteInstanciado,triaje.getSintomas(),triaje.getTemperatura(),triaje.getPresionArterial(), nombreEspecialidad, urgencia);

                // Limpieza visual de la interfaz gráfica de JavaFX
                limpiarCampos();
                System.out.println("Expediente clínico y triaje consolidados con éxito.");
            } else {
                System.out.println("Error local: Falló la escritura en la base de datos.");
            }
        }
    }
    
    
    @FXML
    private void Modificar() throws ParserConfigurationException, TransformerException{
        String urgencia = cbxUrgencia.getValue().toString();
        System.out.println("P = " + pacienteGlobal.toString());
        System.out.println("urgencia = " + urgencia);
        System.out.println("sintomas = " + txtSintomas.getText());

        String nombreEspecialidad = cbxEspecialidad.getSelectionModel().getSelectedItem().toString();
        String sintomas = txtSintomas.getText();
        PacienteDao pacienDao = new PacienteDao();
        Paciente pacienteInstanciado = null;
        TriajeDao triajeDao = new TriajeDao();
        if (urgencia.equalsIgnoreCase("ALTA")) {
            pacienteInstanciado = new PacienteCritico(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                    pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        } else if (urgencia.equalsIgnoreCase("MEDIA")) {
            pacienteInstanciado = new PacienteMedio(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                    pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        } else if (urgencia.equalsIgnoreCase("BAJA")) {
            pacienteInstanciado = new PacienteEstable(
                    1, pacienteGlobal.getNombre_paciente(), pacienteGlobal.getApellido_paciente(), pacienteGlobal.getCarnet(), pacienteGlobal.getSexo(),
                    pacienteGlobal.getFecha_nacimiento(), pacienteGlobal.getTelefono(), pacienteGlobal.getDireccion());

        }

        if (pacienteInstanciado != null) {
            // 3. Crear los objetos de soporte con el nuevo diseño limpio de clases
            Especialidad especialidad = new Especialidad();
            especialidad.setNombreEspecialidad(nombreEspecialidad);

            Triaje triaje = new Triaje();
            triaje.setSintomas(sintomas);
            triaje.setNivel_urgencia(urgencia);

            // Asignamos las lecturas físicas que te hacían falta en pantalla (Temperatura y Presión)
            // Reemplaza 'txtTemperatura' y 'txtPresion' por los nombres reales de tus TextField
            try {
                triaje.setTemperatura(Double.parseDouble(txtTemperatura.getText()));
            } catch (NumberFormatException e) {
                triaje.setTemperatura(36.5); // Valor por defecto seguro si el campo está vacío o erróneo
            }
            triaje.setPresionArterial(txtPresion.getText());

            // Establecer las relaciones bidireccionales en memoria
            triaje.setPaciente(pacienteInstanciado);
            triaje.agregarEspecialidad(especialidad);
            pacienteInstanciado.agregarTriaje(triaje);

            // 4. PERSISTENCIA EN LA BASE DE DATOS
            // Primero aseguramos que el paciente exista o se registre
            boolean pacienteGuardado = pacienDao.modificarRegistro(pacienteInstanciado);

            // Segundo: Invocamos el procedimiento almacenado sp_insertar_triaje a través del DAO
            // Pasamos el objeto triaje completo que contiene las relaciones internas
            boolean triajeGuardado = triajeDao.insertarRegistro(triaje);

            // 5. Si ambos se guardaron en la BD, generamos el XML final de UES-Salud
            if (pacienteGuardado && triajeGuardado) {

                // Usamos tu método limpio de generación de XML
                ExpedienteXML.generarExpedienteXML(pacienteInstanciado, triaje.getSintomas(), triaje.getTemperatura(), triaje.getPresionArterial(), nombreEspecialidad, urgencia);

                // Limpieza visual de la interfaz gráfica de JavaFX
                limpiarCampos();
                System.out.println("Expediente clínico y triaje consolidados con éxito.");
            } else {
                System.out.println("Error local: Falló la sobreescritura en la base de datos.");
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
