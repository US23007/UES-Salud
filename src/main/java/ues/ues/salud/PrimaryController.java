package ues.ues.salud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;


public class PrimaryController implements Initializable{

    @FXML
    private Button btnRegistro;
    
    @FXML
    private BorderPane bPrincipal;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPanel("triaje.fxml");
    }

    @FXML
    private void Registro(){
        cargarPanel("triaje.fxml");
    }
    
    
    public void cargarPanel(String panel){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(panel));
        try {
            Parent root = loader.load();
            
            bPrincipal.setCenter(root);
        } catch (IOException ex) {
            System.out.println("Error al cargar en"+panel + ex);
            ex.getMessage();
        }
    }
   
}
