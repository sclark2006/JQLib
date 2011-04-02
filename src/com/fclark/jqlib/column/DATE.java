package com.fclark.jqlib.column;

import java.util.Date;

import com.fclark.jqlib.Updatable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 15, 2011. Creación de clase DateColumn
 * 
 * Esta clase representa columnas de base de datos definidas con tipo DATE
 */
public class DATE extends Column {

    public DATE(String name) {
        super(name);
    }

    public DATE(String name, Updatable<?> source) {
        super(name, source);
    }
    
    public DATE(String name, Updatable<?> source, int pkPos) {
        super(name,source,pkPos);
    }


    public DATE(String name, Date value) {
        super(name, value);
    }
    
    public void set(Object value) {
        if(value == null || Date.class.isAssignableFrom(value.getClass()))
        {
            super.set(value);
        }
        else
            throw new IllegalArgumentException();
    }
    
    public void set(Date value) {
        super.set(value);
    }
    
    public Date getDate() {
        return (Date)super.get();
    }

}
