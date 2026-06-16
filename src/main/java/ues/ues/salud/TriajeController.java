/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Dao.DoctorDao;
import ues.ues.salud.Dao.EspecialidadDao;
import ues.ues.salud.Dao.PacienteDao;
import ues.ues.salud.Dao.RecetaDao;
import ues.ues.salud.Dao.TriajeDao;
import ues.ues.salud.model.DetalleReceta;
import ues.ues.salud.model.Doctor;
import ues.ues.salud.model.Especialidad;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.PacienteCritico;
import ues.ues.salud.model.PacienteEstable;
import ues.ues.salud.model.PacienteMedio;
import ues.ues.salud.model.Triaje;
import ues.ues.salud.utils.DetalleRecetaTable;
import ues.ues.salud.utils.ExpedienteXML;
import ues.ues.salud.utils.GenerarReportePDF;

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
    private Tab tReceta;
    
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
    private Button btnAtrasReceta;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnModificar;
    
    
    @FXML
     private Button btnGrabar;
    
    
    @FXML 
    private ChoiceBox cbxAtencion;
    
    @FXML
    private TextField txtPacienteReceta;
    
    @FXML
    private TextArea txtRecetaSintomas;
    
    @FXML
    private TableView<DetalleRecetaTable> tblMedicamentos;
    @FXML
    private TableColumn<DetalleRecetaTable, String> colNombre;
    @FXML
    private TableColumn<DetalleRecetaTable, String> colDosis;
    @FXML
    private TableColumn<DetalleRecetaTable, String> colIndicaciones;

    private ObservableList<DetalleRecetaTable> listaMedicamentos = FXCollections.observableArrayList();
    
    @FXML
    private TextArea txtDiagnosticos;
    
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
    //Botón Continuar de tabDatosPersonales 
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
        Object seleccionado = cbxUrgencia.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
           
            tabNivel.getStyleClass().removeAll("urgencia-baja", "urgencia-media", "urgencia-alta");
            return;
        }
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
    //Botón Retroceder de tabNuevoTriaje
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
    //Botón Continuae de tabNuevoTriaje 
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

        if (pacienteInstanciado != null && pacienDao.insertarRegistro(pacienteInstanciado)) {
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

        }
        tInfo.setDisable(true);
        tReceta.setDisable(false);
        tabPane.getSelectionModel().select(tReceta);
        tDatos.setDisable(true);
        DoctorDao docDao = new DoctorDao();
        List<Doctor> doctores = new ArrayList<>();
        System.out.println("doctores = " + cbxEspecialidad.getSelectionModel().getSelectedItem().toString());
        doctores = docDao.listarTodos("nombre_especialidad", cbxEspecialidad.getSelectionModel().getSelectedItem().toString());
        
        doctores.forEach(System.out::println);
        doctores.forEach(dc -> {

            cbxAtencion.getItems().add(dc.getNombre());
        });
        
        txtPacienteReceta.setText(pacienteInstanciado.getNombre_paciente()+ " " + pacienteGlobal.getApellido_paciente());
        txtRecetaSintomas.setText(pacienteInstanciado.getUltimoTriaje().getSintomas());
        vincularTabla();
    }
    
    
    @FXML
    private void Modificar() throws ParserConfigurationException, TransformerException {
      navegarTabs();
    }

    @FXML
    private void Grabar() throws ParserConfigurationException, TransformerException, SQLException{
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

            
            triaje.setPaciente(pacienteInstanciado);
            triaje.agregarEspecialidad(especialidad);
            pacienteInstanciado.agregarTriaje(triaje);

            
            //boolean pacienteGuardado = pacienDao.modificarRegistro(pacienteInstanciado);

            DoctorDao docDao = new DoctorDao();
            int idTriaje  = triajeDao.insertarRegistroConId(triaje);

            System.out.println("DOC = " + cbxAtencion.getSelectionModel().getSelectedItem().toString());
            int idDoctor = docDao.ObtenerDoctor(cbxAtencion.getSelectionModel().getSelectedItem().toString());
            String diagnostico = txtDiagnosticos.getText();
            
            List<DetalleRecetaTable> listaTabla = tblMedicamentos.getItems();
            List<DetalleReceta> medicamentos = new ArrayList<>();
            
            for(DetalleRecetaTable dt : listaTabla){
                if (dt.getNombre().trim().isEmpty() || dt.getNombre().equals("Nuevo Medicamento")) {
                    continue;
                }
                
                DetalleReceta medicamento = new DetalleReceta(dt.getNombre(), dt.getDosis(), dt.getIndicaciones());
               
                medicamentos.add(medicamento);
            }
            
            int idRecetaGenerada = 0;
            RecetaDao receDao = new RecetaDao();
            if (idTriaje > 0) {
                idRecetaGenerada = receDao.insertarRecetaYDetalles(
                        idTriaje,
                        idDoctor,
                        diagnostico,
                        medicamentos
                );
            }
            
            if (idTriaje>0 &&idRecetaGenerada>0 ) {

                // Usamos tu método limpio de generación de XML
                ExpedienteXML.generarExpedienteXML(pacienteInstanciado,triaje.getSintomas(),triaje.getTemperatura(),triaje.getPresionArterial(), nombreEspecialidad, urgencia);
                String nombreDoctor = cbxAtencion.getSelectionModel().getSelectedItem().toString();
                GenerarReportePDF.generarRecetaPDF(pacienteInstanciado, nombreDoctor, nombreEspecialidad, diagnostico, medicamentos);
                
                limpiarCampos();
                System.out.println("Expediente clínico y triaje consolidados con éxito.");
            } else {
                System.out.println("Error local: Falló la escritura en la base de datos.");
            }
        }
    }
    
    @FXML
    private void RetrocederReceta(){
        cbxAtencion.getItems().clear();
        tInfo.setDisable(false);
        tReceta.setDisable(true);
        tabPane.getSelectionModel().select(tInfo);
       
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
    
    
    private void vincularTabla(){
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDosis.setCellValueFactory(cellData -> cellData.getValue().dosisProperty());
        colIndicaciones.setCellValueFactory(cellData -> cellData.getValue().indicacionesProperty());

        
        tblMedicamentos.setEditable(true);
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colDosis.setCellFactory(TextFieldTableCell.forTableColumn());
        colIndicaciones.setCellFactory(TextFieldTableCell.forTableColumn());

        
        colNombre.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNombre(e.getNewValue()));
        colDosis.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setDosis(e.getNewValue()));
        colIndicaciones.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setIndicaciones(e.getNewValue()));

        
        tblMedicamentos.setItems(listaMedicamentos);
    }
    
    
    @FXML
    private void Nuevo(){
        DetalleRecetaTable nuevaFila = new DetalleRecetaTable("Nuevo Medicamento", "Dosis", "Indicaciones");
        listaMedicamentos.add(nuevaFila);

        // Opcional: Seleccionar automáticamente la nueva fila para comodidad del usuario
        tblMedicamentos.getSelectionModel().select(nuevaFila);
    }
    
    @FXML
    private void Menos(){
        DetalleRecetaTable seleccionado = tblMedicamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaMedicamentos.remove(seleccionado);
        } else {
            System.out.println("Por favor, selecciona una fila de la tabla para eliminarla.");
        }
    }
    
    private void navegarTabs(){
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

        }
        tInfo.setDisable(true);
        tReceta.setDisable(false);
        tabPane.getSelectionModel().select(tReceta);
        tDatos.setDisable(true);
        DoctorDao docDao = new DoctorDao();
        List<Doctor> doctores = new ArrayList<>();
        System.out.println("doctores = " + cbxEspecialidad.getSelectionModel().getSelectedItem().toString());
        doctores = docDao.listarTodos("nombre_especialidad", cbxEspecialidad.getSelectionModel().getSelectedItem().toString());
        
        doctores.forEach(System.out::println);
        doctores.forEach(dc -> {

            cbxAtencion.getItems().add(dc.getNombre());
        });
        
        txtPacienteReceta.setText(pacienteInstanciado.getNombre_paciente()+ " " + pacienteGlobal.getApellido_paciente());
        txtRecetaSintomas.setText(pacienteInstanciado.getUltimoTriaje().getSintomas());
        vincularTabla();
    }
}
