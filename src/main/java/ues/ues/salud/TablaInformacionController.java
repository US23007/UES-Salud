/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ues.ues.salud;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class TablaInformacionController <T>{

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
    
}
