/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ues.ues.salud.model.Paciente;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class TablaInformacionController <T> implements Initializable{

    @FXML
    private TableView<T> table;

    public void setColumns(
            String[] nombres,
            String[] propiedades
    ){

        table.getColumns().clear();

        for(int i = 0; i < nombres.length; i++){

            TableColumn<T, Object> col =
                    new TableColumn<>(nombres[i]);

            col.setCellValueFactory(
                    new PropertyValueFactory<>(
                            propiedades[i]
                    )
            );

            table.getColumns().add(col);

        }

    }

    public void setData(
            ObservableList<T> datos
    ){

        table.setItems(datos);

    }
    
    public void configurarMenuEliminar(Consumer<T> accionEliminar) {
        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>(); 
            ContextMenu contextMenu = new ContextMenu();
            MenuItem eliminarItem = new MenuItem("Eliminar");

            

            eliminarItem.setOnAction(event -> {
                T objetoSeleccionado = row.getItem(); 
                if (objetoSeleccionado != null) {
                 
                    accionEliminar.accept(objetoSeleccionado); 
                }
            });

            contextMenu.getItems().add(eliminarItem);

            row.emptyProperty().addListener((obs, wasEmpty, isEmpty) -> {
                if (isEmpty) {
                    row.setContextMenu(null);
                } else {
                    row.setContextMenu(contextMenu);
                }
            });

            return row;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
