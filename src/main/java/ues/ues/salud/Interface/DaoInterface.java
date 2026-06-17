/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ues.ues.salud.Interface;

import java.util.List;

/**
 *
 * @author su487
 */
public interface DaoInterface<T>{
    boolean insertarRegistro(T entidad); //Insertar un nuevo registro a la base de datos
    boolean modificarRegistro(T entidad); //Modificar un registro a la base de datos
    boolean eliminarRegistro(String codigo); //Eliminar un registro a la base de datos
    T buscarRegistro(String codigo); //Buscar un registro a la base de datos
    List<T> listarTodos(String campo,String valor); //Listar todos los registro de la base de datos
    
}
