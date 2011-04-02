package com.fclark.jqlib;

public class SQLClauseException extends java.sql.SQLException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public SQLClauseException() {
        
    }
    
    public SQLClauseException(String message) {
        super(message);
    }

}
