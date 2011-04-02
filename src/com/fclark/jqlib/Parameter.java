package com.fclark.jqlib;


public class Parameter extends Expression {
    private Object value;

    public static final Parameter $PAR = new Parameter("?");
    
    public Parameter(Object value) {
        super(value instanceof String && !value.equals("?") ? 
                "'" + value.toString() + "'" : value.toString());
        this.value = value;
    }
    
    public Parameter(String name, Object value) {
        super(name);
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
        
    public static Parameter $(Object val) {
        return new Parameter(val);
    }
    
}
