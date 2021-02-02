package com.fclark.jqlib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Feb 10, 2011. Creación interfaz Resultable
 * @version 0.2 Feb 16, 2011. Se agregó el método asQuery, que retorna un IQuery
 * @version 0.3 Mar 1, 2011. Se agregaron los métodos asIterable() y asList()
 * @version 0.4 Mar 10, 2011. Se renombró el método getRecord() a getFirst(). Las métodos orderBy() y having() se 
 *                            movieron a las interfaces OrderClause y HavingClause, respectivamente.
 * @version 0.5 Mar 14, 2011. Adición de throws SQLClauseException al método toQuery().
 * @version 0.6 Mar 16, 2011. Resultable hereda de la interfaz Executable
 *                             
 */
public interface Resultable extends Executable {
    static enum FetchCriteria {
        RESULTSET_COLUMNS, OBJECT_PROPERTIES, ENTITY_COLUMNS
    }
    Expression[] getColumns();
    Queryable<?> toQuery() throws SQLClauseException;
    ResultSet asResultSet() throws SQLException;
    ResultSet asResultSet(Connection conn) throws SQLException;
    ResultSet asResultSet(Connection conn, Object... params) throws SQLException;
    ResultSet asResultSet(Object... params) throws SQLException;
    <T extends Entity<T>> Iterable<T> asIterable(final Class<T> type) throws Exception;
    <T extends Entity<T>> Iterable<T> asIterable(final Class<T> type, Object... params) throws Exception;
    <T extends Entity<T>> List<T> asList(Class<T> type) throws Exception;
    <T extends Entity<T>> List<T> asList(Class<T> type, Object... params) throws Exception;    
    void fetchInto(Object[] columns) throws Exception;    
    void fetchInto(Object[] columns, Object... params) throws Exception;    
    <T> T getSingleValue() throws Exception;
    <T> T getSingleValue(Object... params) throws Exception;
    <T> T getSingleValue(Class<T> type, Object... params) throws Exception;
    <T> T getFirst(Class<T> type) throws Exception;   
    <T> T getFirst(Class<T> type, Object... params) throws Exception;    
    <T> T getFirst(Class<T> type, FetchCriteria fetchCriteria, Object... params) throws Exception;
    void setConnection(Connection conn);
    Connection getConnection();    
    boolean found();
    void close();
    
}
