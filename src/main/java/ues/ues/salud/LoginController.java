package ues.ues.salud;

import ues.ues.salud.conexion.Conexion;
import ues.ues.salud.utils.ConfigDB;
import java.io.IOException;
import java.io.ObjectInputFilter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

public class LoginController implements Initializable{
    
    
    @FXML
    private TextField txtRoot;
    
    @FXML
    private TextField txtContrasenia;
    
    @FXML
    private TextField txtHost;
    
    @FXML
    private TextField txtPuerto;
    
    @FXML
    private TextField txtDB;
    
    @FXML
    private ComboBox cbxMetodo;
    
    @FXML 
    private ImageView imgConfig;
    
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnConectar;
    
    @FXML
    private Button btnCancelar;
    
  
    
    @FXML
    private void conectar() throws SQLException, IOException{
        if(txtRoot.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Usuario esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            
            return;
        
        }
        if(txtContrasenia.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Contraseña esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            return;
        }
        if(txtHost.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Host esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
        
            return;
        }
        
        if(txtPuerto.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Puerto esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            return;
        }
        
        if(txtDB.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Base de Datos esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
        
            return;
                    
        }
        
        Conexion con = new Conexion();
        boolean conectado = con.getConexion(txtHost.getText(),txtPuerto.getText(),txtDB.getText(),txtRoot.getText(),txtContrasenia.getText());
        
        if(conectado){
            
            
            
            System.out.println("User = " + ConfigDB.getUser());
            Notifications.create()
                    .title("Conexión Exitosa")
                    .text("Conexión establecida a la Base de Datos.")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showInformation();
            try{
                
                App.setRoot("primary");
                
            }catch(Exception ex){
                System.out.println("ex = " + ex);
                
            }
        }else{
            
            Notifications.create()
                    .title("Acceso Invalido")
                    .text("Las credenciales ingresadas son incorrectas")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showError();
            
            
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarConfig();
    }
    
    
    @FXML 
    private void Configurar(){
        btnConectar.setVisible(false);
        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
    }
    
    
    @FXML
    private void Guardar() throws IOException{
        if(txtRoot.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Usuario esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            
            return;
        
        }
        if(txtContrasenia.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Contraseña esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            return;
        }
        if(txtHost.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Host esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
        
            return;
        }
        
        if(txtPuerto.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Puerto esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
            return;
        }
        
        if(txtDB.getText().trim().isEmpty()){
            Notifications.create()
                    .title("Campo Vacio")
                    .text("El campo Base de Datos esta vacio")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showWarning();
        
            return;
                    
        }
        ConfigDB.setHost(txtHost.getText());
        ConfigDB.setPort(txtPuerto.getText());
        ConfigDB.setDB(txtDB.getText());
        ConfigDB.setUser(txtRoot.getText());
        ConfigDB.setPassword(txtContrasenia.getText());
        ConfigDB.guardarConfiguracion();
        
        Notifications.create()
                    .title("Credenciales Actualizadas")
                    .text("Las credenciales han sido actualizadas")
                    .hideAfter(Duration.seconds(3)) // Se cierra sola tras 3 segundos
                    .position(Pos.BOTTOM_RIGHT) // Aparece elegantemente abajo a la derecha
                    .showInformation();
        
        Cancelar();
    }
    
    @FXML
    private void Cancelar(){
        btnConectar.setVisible(true);
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        cargarConfig();
    }
    
    private void cargarConfig(){
        txtRoot.setText(ConfigDB.getUser());
        txtHost.setText(ConfigDB.getHost());
        txtPuerto.setText(ConfigDB.getPort());
        txtContrasenia.setText(ConfigDB.getPassword());
        txtDB.setText(ConfigDB.getDB());
    }
}
