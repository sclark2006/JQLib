package com.fclark.jqlib.column;

import com.fclark.jqlib.Updatable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 15, 2011. Creación de clase VarcharColumn
 * 
 * Esta clase representa columnas de base de datos definidas con tipo VARCHAR
 */
public class VARCHAR extends Column {

    public VARCHAR(String name) {
        super(name);
    }

    public VARCHAR(String name, Updatable<?> source) {
        super(name, source);
    }
    
    public VARCHAR(String name, Updatable<?> source, int pkPos) {
        super(name,source,pkPos);
    }

    public VARCHAR(String name, Object value) {
        super(name, value);
    }    

    public void set(Object value) {
        if(value == null || value instanceof String)
        {
            super.set(value);
        }
        else
            throw new IllegalArgumentException();
    }
    
    public void set(String value) {
        super.set(value);
    }
    
    public String getString() {
        return (String)super.get();
    }
}
