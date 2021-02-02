package com.fclark.jqlib;

import java.sql.Connection;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 11, 2011. Creaci�n de la case.
 * 
 * Esta clase sirve para almacenar variables de uso com�n.
 *
 */
public class Environment {
    private static Connection s_conn;    
    
    public static void setConnection(Connection conn) {
        s_conn = conn; 
    }
    
    public static Connection getConnection() {
        return s_conn;
    }
}
