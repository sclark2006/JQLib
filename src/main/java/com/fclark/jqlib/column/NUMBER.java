package com.fclark.jqlib.column;

import com.fclark.jqlib.Updatable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 15, 2011. Creación de clase NumericColumn
 * 
 * Esta clase representa columnas de base de datos definidas con tipo numérico (INTEGER, NUMERIC, NUMBER, etc)
 */
public class NUMBER extends Column {

    public NUMBER(String name) {
        super(name);
    }

    public NUMBER(String name, Updatable<?> source) {
        super(name, source);
    }
    
    public NUMBER(String name, Updatable<?> source, int pkPos) {
        super(name,source,pkPos);
    }

    public NUMBER(String name, Number value) {
        super(name, value);
    }
    
    public void set(Object value) {
        if(value == null || Number.class.isAssignableFrom(value.getClass()))
        {
            super.set(value);
        }
        else
            throw new IllegalArgumentException();
    }
    
    public void set(Short value) {
        super.set(value);
    }
    
    public void set(Integer value) {
        super.set(value);
    }
    
    public void set(Long value) {
        super.set(value);
    }

    public void set(Float value) {
        super.set(value);
    }
    
    public void set(Double value) {
        super.set(value);
    }
    
    public Short getShort() {
        return (Short)super.get();
    }
    
    public Integer getInt() {
        return (Integer)super.get();
    }
    
    public Long getLong() {
        return (Long)super.get();
    }
    
    public Float getFloat() {
        return (Float)super.get();
    }
    
    public Double getDouble() {
        return (Double)super.get();
    }
}
