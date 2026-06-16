/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
 *
 * @author su487
 */
public class ReporteController implements Initializable {

    @FXML PieChart pieChartEspecialidades;
    @FXML PieChart pieChartUrgencias;
    @FXML PieChart pieChartGenero;
    @FXML PieChart pieChartMedicamentos;
    
    ReporteDao reporDao = new ReporteDao();
                    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarGraficoEspecialidades();
            cargarGraficoGeneros();
            cargarGraficoMedicamentos();
            cargarGraficoUrgencias();
        } catch (SQLException ex) {
            Logger.getLogger(ReporteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void cargarGraficoEspecialidades() throws SQLException {
        pieChartEspecialidades.getData().clear();
        ObservableList<PieChart.Data> datosPie = FXCollections.observableArrayList();
        
        Map<String,Number> datosMap = reporDao.obtenerPacientesPorEspecialidad();
        
        
        
        datosMap.forEach((especialidad,total)->{ 
           String etiquetaConDatos = especialidad + " (" + total + ")";
            datosPie.add(new PieChart.Data(etiquetaConDatos, total.doubleValue()));
        
        });
        
        pieChartEspecialidades.setData(datosPie);
        
        pieChartEspecialidades.setLabelsVisible(true);
    }
    
    private void cargarGraficoUrgencias() {
        pieChartUrgencias.getData().clear();
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        reporDao.obtenerPacientesPorUrgencia().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
        pieChartUrgencias.setData(datos);
    }

    private void cargarGraficoGeneros() {
        pieChartGenero.getData().clear();
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        reporDao.obtenerPacientesPorGenero().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
        pieChartGenero.setData(datos);
    }

    private void cargarGraficoMedicamentos() {
        pieChartMedicamentos.getData().clear();
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        reporDao.obtenerMedicamentosMasRecetados().forEach((key, val) -> {
            datos.add(new PieChart.Data(key + " (" + val + ")", val.doubleValue()));
        });
        pieChartMedicamentos.setData(datos);
    }
    
}
