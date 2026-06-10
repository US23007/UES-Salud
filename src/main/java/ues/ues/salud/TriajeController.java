/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author su487
 */
public class TriajeController implements Initializable {

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxUrgencia.getItems().add("BAJA");
        cbxUrgencia.getItems().add("MEDIA");
        cbxUrgencia.getItems().add("ALTA");
    }    
    
    
    @FXML
    private void Siguiente(){
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
}
