
package ues.ues.salud.Interface;

import java.util.List;

/**
 * DaoInterfaz : ya que estamos haciendo procesos similares entre las entidades se decidio generalizar e implementar en cada una de las entidades metodos generales para ser sobreescritos a nuestra conveniencia
 *Fecha : 04/06/2026
 * Autor: US23007 Samuel De Jesus Umaña Sorto
 */
public interface DaoInterface<T>{
    boolean insertarRegistro(T entidad); //Insertar un nuevo registro a la base de datos
    boolean modificarRegistro(T entidad); //Modificar un registro a la base de datos
    boolean eliminarRegistro(String codigo); //Eliminar un registro a la base de datos
    T buscarRegistro(String codigo); //Buscar un registro a la base de datos
    List<T> listarTodos(String campo,String valor); //Listar todos los registro de la base de datos
    
}
