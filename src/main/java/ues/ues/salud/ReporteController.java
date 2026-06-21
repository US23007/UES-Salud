
package ues.ues.salud;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import ues.ues.salud.Dao.ReporteDao;

/**
 * FXML Controller class
 *  Clase ReporteController : es el encargado de obtener la informacion de la base de datos para nuestras estadisticas y reportes
 * Autor:US23007 Samuel De Jesus Umaña Sorto
 * 
 */
public class ReporteController implements Initializable {

    //PieCharts
    @FXML PieChart pieChartEspecialidades;
    @FXML PieChart pieChartUrgencias;
    @FXML PieChart pieChartGenero;
    @FXML PieChart pieChartMedicamentos;
    
    ReporteDao reporDao = new ReporteDao();
                    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Cargamos todos los graficos al iniciar 
            cargarGraficoEspecialidades();
            cargarGraficoGeneros();
            cargarGraficoMedicamentos();
            cargarGraficoUrgencias();
        } catch (SQLException ex) {
            Logger.getLogger(ReporteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    //Cargamos los datos de la clase ReporteDao y se lo asignamos al grafico de Especialidades Atendidas
    private void cargarGraficoEspecialidades() throws SQLException {
        pieChartEspecialidades.getData().clear();
        ObservableList<PieChart.Data> datosPie = FXCollections.observableArrayList();
        
        //Creamos el HasMap para el PieChart
        Map<String,Number> datosMap = reporDao.obtenerPacientesPorEspecialidad();
        
        
        //Asignamos los datos
        datosMap.forEach((especialidad,total)->{ 
           String etiquetaConDatos = especialidad + " (" + total + ")";
            datosPie.add(new PieChart.Data(etiquetaConDatos, total.doubleValue()));
        
        });
        //Agregamos la informacion a al grafico
        pieChartEspecialidades.setData(datosPie);
        
        pieChartEspecialidades.setLabelsVisible(true);
    }
    
    //Cargamos los datos de la clase ReporteDao y se lo asignamos al grafico de Urgencias Atendidas
    private void cargarGraficoUrgencias() {
        pieChartUrgencias.getData().clear();
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        //Creamos el HasMap para el PieChart
        reporDao.obtenerPacientesPorUrgencia().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
         //Agregamos la informacion a al grafico
        pieChartUrgencias.setData(datos);
    }

    
    //Cargamos los datos de la clase ReporteDao y se lo asignamos al grafico de Genero Atendidas
    private void cargarGraficoGeneros() {
        pieChartGenero.getData().clear();
        //Creamos el HasMap para el PieChart
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        reporDao.obtenerPacientesPorGenero().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
         //Agregamos la informacion a al grafico
        pieChartGenero.setData(datos);
    }
    
    
    //Cargamos los datos de la clase ReporteDao y se lo asignamos al grafico de Medicamentos más recetados
    private void cargarGraficoMedicamentos() {
        pieChartMedicamentos.getData().clear();
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
         //Creamos el HasMap para el PieChart
        reporDao.obtenerMedicamentosMasRecetados().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
        
        //Agregamos la informacion a al grafico
        pieChartMedicamentos.setData(datos);
    }
    
}
