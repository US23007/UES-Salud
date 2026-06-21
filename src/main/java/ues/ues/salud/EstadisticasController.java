
package ues.ues.salud;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ues.ues.salud.Dao.PacienteDao;
import ues.ues.salud.Dao.ReporteDao;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.utils.GenerarReportePDF;

/**
 * Controlador del módulo de estadísticas y reportes.
 * Permite generar reportes PDF de atenciones médicas,
 * expedientes clínicos y síntomas más frecuentes.
 * Documentado por: Astrid Escobar PE25005 APE115
 */


// Método ejecutado al iniciar la vista de estadísticas.
public class EstadisticasController implements Initializable {

    @FXML Button btnReporteAtenciones;
    @FXML DatePicker dtInicio;
    @FXML DatePicker dtFinal;
    @FXML DatePicker dtInicioSintomas;
    @FXML DatePicker dtFinalSintomas;
    @FXML TextField txtCarnet;
    @FXML Button btnExpediente,btnReporteSintomas;
    ReporteDao repoDao =new ReporteDao();
    
    @FXML 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // Genera un reporte PDF de las atenciones realizadas
    // dentro de un rango de fechas seleccionado por el usuario.
    @FXML
    private void ReporteAtenciones(){
        
        LocalDate inicio = dtInicio.getValue();
        LocalDate fin = dtFinal.getValue();
        if (inicio == null || fin == null) {
            Notifications.create()
                        .title("Campos Vacios")
                        .text("Por favor selecciona un rango de fechas válido (Inicio y Fin).")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showWarning();
            return;
        }

        if (inicio.isAfter(fin)) {
            Notifications.create()
                        .title("Rango Incorrecto")
                        .text("La fecha de inicio no puede ser posterior a la fecha final.")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showError();
            return;
        }
        
        List<String[]> datosReporte = repoDao.obtenerAtencionesPorFecha(inicio, fin);
        GenerarReportePDF.generarReporteAtenciones(inicio, fin, datosReporte);
        Limpiar();
    }
    
    
    // Limpia los campos utilizados para generar reportes.
    private void Limpiar(){
        dtInicio.setValue(null);
        dtFinal.setValue(null);
        txtCarnet.setText("");
        dtFinalSintomas.setValue(null);
        dtInicioSintomas.setValue(null);
    }
    
    
    // Genera el expediente clínico de un paciente a partir
    // del carnet ingresado por el usuario.
    @FXML
    private void ReporteExpediente(){
        PacienteDao paciDao = new PacienteDao();
        String carnet = txtCarnet.getText().trim();

        if (carnet.isEmpty()) {
            Notifications.create()
                        .title("Campos Vacios")
                        .text("Por favor ingrese el Carnet del Paciente")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showWarning();
            return;
        }
        
        Paciente paciente  = paciDao.buscarRegistro(carnet);
        
        if(paciente.getCarnet() == null){
            Notifications.create()
                        .title("Operación Fallida")
                        .text("El Carnet Ingresado es Invalido o no Existe")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showError();
            return;
        }else{
            List<String[]> historialConsultas = repoDao.obtenerHistorialPorCarnet(carnet);
            GenerarReportePDF.generarExpedienteClinico(paciente, historialConsultas);
            
        }
        
        Limpiar();
       
    }
    
    
    // Genera un reporte PDF con los síntomas más frecuentes
    // registrados en un período determinado.
     @FXML
     private void ReporteSintomas(){
         LocalDate inicio = dtInicioSintomas.getValue();
        LocalDate fin = dtFinalSintomas.getValue();
        if (inicio == null || fin == null) {
            Notifications.create()
                        .title("Campos Vacios")
                        .text("Por favor selecciona un rango de fechas válido (Inicio y Fin).")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showWarning();
            return;
        }

        if (inicio.isAfter(fin)) {
            Notifications.create()
                        .title("Rango Incorrecto")
                        .text("La fecha de inicio no puede ser posterior a la fecha final.")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showError();
            return;
        }
        
        List<String[]> datosSintomas = repoDao.obtenerSintomasMasComunes(inicio, fin);
        GenerarReportePDF.generarReporteSintomas(inicio, fin, datosSintomas);
        Limpiar();
     }
}
