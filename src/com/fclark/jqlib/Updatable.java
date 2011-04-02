package com.fclark.jqlib;

import java.sql.SQLException;

import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 21, 2011. Creación de Interfaz Updatable<T>
 * @version 0.2 Mar 25, 2011. Creación de método Key getPrimaryKey()
 * @version 0.3 Mar 29, 2011. Adición de métodos findByPK(), findFirstBy(), findWhere(), findBy,
 */
public interface Updatable<T extends Updatable<T>> extends Aliasable<T> {
    void setName(String name);
    String getName();
    String aliasedName();
        
    
    /**
     * Obtiene la clave primaria de la entidad representada por este objeto {@link Updatable}.
     * 
     * @return              Un objeto {@link Key} con las columnas que forman la clave primaria.
     */
    Key getPrimaryKey();
    void notifyValueChange(Assignable source);
    
    /**
     * Retorna las columnas que pertenecen a esta entidad.  
     * 
     * @return              Un arreglo de objetos {@link Column} que representan las columnas de la entidad 
     * representada por este objeto {@link Updatable}. 
     */    
    Column[] columns();
    
    /**
     * Retorna los valores de los {@link Column} que pertenecen a esta entidad.  
     * 
     * @return              Un arreglo de objetos con los valores de las columnas de este objeto {@link Updatable}. 
     */
    Object[] values();
    
    /**
     * Inserta un nuevo registro en la entidad que actual, usando los valores almacenados en cada campo.
     *
     * @return              Un entero indicando cuántos registros fueron afectados por la ejecución de este método.
     * @throws SQLException En caso de ocurrir algún problema durante la ejecución del comando DML.
     */
    int insert() throws SQLException;

    
    /**
     * Inserta un nuevo registro en la entidad que actual, usando los valores especificados por el parámetro <i>values</i>.
     *
     * @param values        Arreglo de objetos que serán insertados en la entidad de base de datos. 
     * @return              Un entero indicando cuántos registros fueron afectados por la ejecución de este método.
     * @throws SQLException En caso de ocurrir algún problema durante la ejecución del comando DML.
     */
    int insert(Object... values) throws SQLException;
    
    
    /**
     * Actualiza los campos que han sufrido modificaciones en el registro actual representado por este objeto Updatable.<br>
     * Los objetos que pueden ser actualizados son retornados por un método find() o findXXXX(...);
     *
     * @return              Un entero indicando cuántos registros fueron afectados por la ejecución de este método.
     * @throws SQLException En caso de ocurrir algún problema durante la ejecución del comando DML.
     */
    int update() throws SQLException;
    
    /**
     * Actualiza los campos que han sufrido modificaciones en el registro actual representado por este objeto {@link Updatable} o
     * si este registro no existe en la base de datos, entonces lo inserta.
     * Los objetos que pueden ser actualizados son retornados por un método find() o findXXXX(...);
     *
     * @return              Un entero indicando cuántos registros fueron afectados por la ejecución de este método.
     * @throws SQLException En caso de ocurrir algún problema durante la ejecución del comando DML.
     */
    int save() throws SQLException;

   
    /**
     * Realiza una búsqueda en la entidad representada por esta clase {@link Updatable} usando la clave primaria de la entidad,  
     * y comparándola a los valores especificados en el arreglo variable <i>values</i>.
     *  
     * @param values        Un arreglo variable de objetos.
     * @return              Un objeto de tipo T, que implementa la interfaz {@link Updatable}, que representa un registro de esta entidad.
     */
    T find(Object... values);
    T findFirstBy(Column column, Object value);
    T findFirstWhere(Predicable predicate);
    
    /**  Busca todos los registros contenidos en la entidad representada por este objeto {@link Updatable}.
     * 
     * @return              Una colección de objetos tipo T, con la representación de los registros obtenidos. 
     */
    Iterable<T> findAll();
    Iterable<T> findBy(Column column, Object value);
    Iterable<T> findWhere(Predicable predicate);    
        
    
    /**
     *  Crea una consulta SQL de todas las columnas de esta entidad, que puede ser extendida mediante cláusulas de 
     *  condición o simplemente, ejecutada.
     *  
     * @return              Un objeto Queryable<T> con la representación de una consulta SQL;
     */
    Queryable<T> toQuery();
}
