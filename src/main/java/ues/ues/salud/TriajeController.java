/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.time.LocalDate;
import java.time.Period;
import javafx.scene.control.Alert;
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
import javafx.scene.control.ToggleGroup;
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
    
    @FXML 
    private Button btnModificarDatos;
    
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
        txtTelefono.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (txtTelefono.getText().length() > 8) {
                txtTelefono.setText(txtTelefono.getText().substring(0, 8));
            }  
        });
        txtNombres.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]*")) {
                txtNombres.setText(newValue.replaceAll("[^A-Za-zÁÉÍÓÚáéíóúÑñ ]", "")
                );
            }
        });
        txtApellidos.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]*")) {
                txtApellidos.setText(newValue.replaceAll("[^A-Za-zÁÉÍÓÚáéíóúÑñ ]", ""));
            }
        });
        txtCarnet.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() > 7) {
                txtCarnet.setText(oldValue);
            }
        });
        txtCarnet.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.equals(newValue.toUpperCase())) {
                txtCarnet.setText(newValue.toUpperCase());
            }
        });
        ToggleGroup sexoGroup = new ToggleGroup();
        cbxHombre.setToggleGroup(sexoGroup);
        cbxMujer.setToggleGroup(sexoGroup);
        
        txtTemperatura.textProperty().addListener((o,v,n) -> {
            if(!n.matches("\\d*(\\.\\d*)?")){
                txtTemperatura.setText(v);
            }
        });
        
        txtPresion.textProperty().addListener((o,v,n) -> {
            if(!n.matches("[0-9/]*")){
                txtPresion.setText(v);
            }
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
            btnModificarDatos.setVisible(Modificar);
            txtCarnet.setText(paciente.getCarnet());
            txtCarnet.setDisable(Modificar);
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
       String carnet = txtCarnet.getText().trim();
    String nombres = txtNombres.getText().trim();
    String apellidos = txtApellidos.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String direccion = txtDireccion.getText().trim();

    // VALIDAR CARNET
    if(!carnet.matches("^[A-Z]{2}[0-9]{5}$")){
        mostrarError("El carnet debe tener un formato como: MH25061");
        return;
    }

    // VALIDAR NOMBRES
    if(nombres.isEmpty()){
        mostrarError("Debe ingresar los nombres del paciente");
        return;
    }

    if(!nombres.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")){
        mostrarError("Los nombres solo pueden contener letras");
        return;
    }

    // VALIDAR APELLIDOS
    if(apellidos.isEmpty()){
        mostrarError("Debe ingresar los apellidos del paciente");
        return;
    }

    if(!apellidos.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")){
        mostrarError("Los apellidos solo pueden contener letras");
        return;
    }

    // VALIDAR FECHA DE NACIMIENTO
    if(dtFechaNacimiento.getValue() == null){
        mostrarError("Debe seleccionar una fecha de nacimiento");
        return;
    }

    int edad = Period.between(
            dtFechaNacimiento.getValue(),
            LocalDate.now()
    ).getYears();

    if(edad < 17){
        mostrarError("El paciente debe tener al menos 17 años");
        return;
    }

    if(edad > 90){
        mostrarError("La edad máxima permitida es 90 años");
        return;
    }

    // VALIDAR SEXO
    if(!cbxHombre.isSelected() && !cbxMujer.isSelected()){
        mostrarError("Debe seleccionar el sexo del paciente");
        return;
    }

    // VALIDAR TELEFONO
    if(!telefono.matches("^[0-9]{8}$")){
        mostrarError("El teléfono debe contener exactamente 8 números");
        return;
    }

    // VALIDAR DIRECCION
    if(direccion.isEmpty()){
        mostrarError("Debe ingresar una dirección");
        return;
    }

    // LIMPIAR ESPACIOS REPETIDOS
    nombres = nombres.replaceAll("\\s+", " ");
    apellidos = apellidos.replaceAll("\\s+", " ");

    // CREAR PACIENTE
    pacienteGlobal = new Paciente();

    pacienteGlobal.setNombre_paciente(nombres);
    pacienteGlobal.setApellido_paciente(apellidos);
    pacienteGlobal.setCarnet(carnet);

    if(cbxHombre.isSelected()){
        pacienteGlobal.setSexo(cbxHombre.getText());
    }else{
        pacienteGlobal.setSexo(cbxMujer.getText());
    }

    System.out.println("PACIENTE FECHA UI = " + dtFechaNacimiento.getValue());

    LocalDateTime fecha = dtFechaNacimiento.getValue().atStartOfDay();

    System.out.println("PACIENTE FECHA = " + fecha);

    pacienteGlobal.setFecha_nacimiento(fecha);
    pacienteGlobal.setTelefono(telefono);
    pacienteGlobal.setDireccion(direccion);

    tInfo.setDisable(false);
    tabPane.getSelectionModel().select(tInfo);
    tDatos.setDisable(true);
        
        
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
        // VALIDAR ESPECIALIDAD
        if(cbxEspecialidad.getSelectionModel().getSelectedItem() == null){
            mostrarError("Debe seleccionar una especialidad");
            return;
        }
        //VALIDAR SINTOMAS
        String sintomas = txtSintomas.getText().trim();

        if(sintomas.isEmpty()){
            mostrarError("Debe describir los síntomas del paciente");
            return;
        }
        // VALIDAR TEMPERATURA
        if(txtTemperatura.getText().trim().isEmpty()){
            mostrarError("Debe ingresar la temperatura");
            return;
        }
        double temperatura;

        try{
            temperatura = Double.parseDouble(txtTemperatura.getText().trim());
            if(temperatura < 28 || temperatura > 45){
                mostrarError("La temperatura debe estar entre 28°C y 45°C");
                return;
            }

        }catch(NumberFormatException e){
            mostrarError("La temperatura debe ser numérica");
            return;
        }
        // VALIDAR PRESION
        String presion = txtPresion.getText().trim();
        if(!presion.matches("^\\d{2,3}/\\d{2,3}$")){
            mostrarError("La presión arterial debe tener formato 120/80");
            return;
        }
        // VALIDAR URGENCIA
        if(cbxUrgencia.getValue() == null){
            mostrarError("Debe seleccionar un nivel de urgencia");
            return;
        }
        String urgencia = cbxUrgencia.getEditor().getText().trim();
        if(!urgencia.equalsIgnoreCase("ALTA") && !urgencia.equalsIgnoreCase("MEDIA") && !urgencia.equalsIgnoreCase("BAJA")){
            mostrarError("Debe seleccionar una urgencia válida");
            return;
        }
        System.out.println("P = " + pacienteGlobal.toString());
        System.out.println("urgencia = " + urgencia);
        System.out.println("sintomas = " + txtSintomas.getText());

        String nombreEspecialidad = cbxEspecialidad.getSelectionModel().getSelectedItem().toString();
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
        // VALIDAR MÉDICO
        if(cbxAtencion.getSelectionModel().getSelectedItem() == null){
            mostrarError("Debe seleccionar el médico que atenderá al paciente");
            return;
        }
        // VALIDAR DIAGNÓSTICO
        String diagnostico = txtDiagnosticos.getText().trim();
        if(diagnostico.isEmpty()){
            mostrarError("Debe ingresar un diagnóstico");
            return;
        }

        if(diagnostico.length() < 5){
            mostrarError("El diagnóstico es demasiado corto");
            return;
        }
        // VALIDAR MEDICAMENTOS
        List<DetalleRecetaTable> listaTabla = tblMedicamentos.getItems();

        for(DetalleRecetaTable dt : listaTabla){
            String nombre = dt.getNombre() == null ? "" : dt.getNombre().trim();
            String dosis = dt.getDosis() == null ? "" : dt.getDosis().trim();
            String indicaciones = dt.getIndicaciones() == null ? "" : dt.getIndicaciones().trim();
            // Nombre
            if(nombre.isEmpty()){
                mostrarError("Existe un medicamento sin nombre");
                return;
            }
            if(nombre.equalsIgnoreCase("Nuevo Medicamento")){
                mostrarError("Debe cambiar el nombre de todos los medicamentos");
                return;
            }
            // Dosis
            if(dosis.isEmpty()){
                mostrarError("Existe un medicamento sin dosis");
                return;
            }

            if(dosis.equalsIgnoreCase("Dosis")){
                mostrarError("Debe modificar la dosis de todos los medicamentos");
                return;
            }
            // Indicaciones
            if(indicaciones.isEmpty()){
                mostrarError("Existen medicamentos sin indicaciones");
                return;
            }
            if(indicaciones.equalsIgnoreCase("Indicaciones")){
                mostrarError("Debe modificar las indicaciones de todos los medicamentos");
                return;
            }
}
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

            
            boolean pacienteGuardado = pacienDao.modificarRegistro(pacienteInstanciado);

            DoctorDao docDao = new DoctorDao();
            int idTriaje  = triajeDao.insertarRegistroConId(triaje);

            System.out.println("DOC = " + cbxAtencion.getSelectionModel().getSelectedItem().toString());
            int idDoctor = docDao.ObtenerDoctor(cbxAtencion.getSelectionModel().getSelectedItem().toString());
            
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
        tReceta.setDisable(true);
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
    
    @FXML
    private void ModificarPaciente(){
         String carnet = txtCarnet.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        
        // VALIDAR NOMBRES
        if (nombres.isEmpty()) {
            mostrarError("Debe ingresar los nombres del paciente");
            return;
        }

        if (!nombres.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            mostrarError("Los nombres solo pueden contener letras");
            return;
        }

        // VALIDAR APELLIDOS
        if (apellidos.isEmpty()) {
            mostrarError("Debe ingresar los apellidos del paciente");
            return;
        }

        if (!apellidos.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            mostrarError("Los apellidos solo pueden contener letras");
            return;
        }

        // VALIDAR FECHA DE NACIMIENTO
        if (dtFechaNacimiento.getValue() == null) {
            mostrarError("Debe seleccionar una fecha de nacimiento");
            return;
        }

        int edad = Period.between(
                dtFechaNacimiento.getValue(),
                LocalDate.now()
        ).getYears();

        if (edad < 17) {
            mostrarError("El paciente debe tener al menos 17 años");
            return;
        }

        if (edad > 90) {
            mostrarError("La edad máxima permitida es 90 años");
            return;
        }

        // VALIDAR SEXO
        if (!cbxHombre.isSelected() && !cbxMujer.isSelected()) {
            mostrarError("Debe seleccionar el sexo del paciente");
            return;
        }

        // VALIDAR TELEFONO
        if (!telefono.matches("^[0-9]{8}$")) {
            mostrarError("El teléfono debe contener exactamente 8 números");
            return;
        }

        // VALIDAR DIRECCION
        if (direccion.isEmpty()) {
            mostrarError("Debe ingresar una dirección");
            return;
        }

        // LIMPIAR ESPACIOS REPETIDOS
        nombres = nombres.replaceAll("\\s+", " ");
        apellidos = apellidos.replaceAll("\\s+", " ");

        // CREAR PACIENTE
        pacienteGlobal = new Paciente();

        pacienteGlobal.setNombre_paciente(nombres);
        pacienteGlobal.setApellido_paciente(apellidos);
        pacienteGlobal.setCarnet(carnet);

        if (cbxHombre.isSelected()) {
            pacienteGlobal.setSexo(cbxHombre.getText());
        } else {
            pacienteGlobal.setSexo(cbxMujer.getText());
        }

        System.out.println("PACIENTE FECHA UI = " + dtFechaNacimiento.getValue());

        LocalDateTime fecha = dtFechaNacimiento.getValue().atStartOfDay();

        

        pacienteGlobal.setFecha_nacimiento(fecha);
        pacienteGlobal.setTelefono(telefono);
        pacienteGlobal.setDireccion(direccion);
        PacienteDao paciDao = new PacienteDao();
        if(!paciDao.modificarRegistro(pacienteGlobal)){
             Notifications.create()
                    .title("Error en la base de datos")
                    .text("No se puedo modificar al Paciente con Carnet: "+pacienteGlobal.getCarnet())
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT) 
                    .showError();
        }
        limpiarCampos();
    }
}
