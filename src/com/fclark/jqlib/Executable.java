package com.fclark.jqlib;

import java.sql.SQLException;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 14, 2011. Creaci�n de Interfaz.
 * 
 * Esta interfaz garantiza la ejecuci�n de mandatos SQL DML (insert, update, delete)
 */
public interface Executable {
    int execute() throws SQLException;
    int execute(Object... params) throws SQLException;
    boolean found();
}
