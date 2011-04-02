package com.fclark.jqlib;

import java.util.Vector;

import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 21, 2011. Creación de clase Key.
 * Esta clase representa un conjunto de campos utilizados para mantener la integridad referencial de una entidad.  
 * En esta versión 0.1 solo se usa para indicar los campos que forman un Primary Key. 
 */
public class Key extends Vector<Column> {

    private static final long serialVersionUID = -494235355057092719L;


    /**
     * Retorna una representación en cadena del este {@link Vector}de {@link Column}, en forma de predicado, ejemplo: <br>
     *  CampoClave1 = valor1 ...
     */
    public String toString() {
        return this.toPredicate().toString();
    }

    /**
     * 
     * Indica si esta colección de columnas está vacía o no.
     */
    public boolean isEmpty() {
        return super.size() == 0;
    }
    
    /**
     * Obtiene los valores de los objeto {@link Column}que componen este objeto {@link Key}.
     * 
     * @return Un arreglo de objetos con los valores de las columnas de este objeto {@link Key} 
     */
    public Object[] values() {
        Object[] result = new Object[this.elementCount];
        
        for(int i = 0; i < this.elementCount; i++) {
            result[i] = Column.class.cast(this.elementData[i]).get();
        }
        
        return result;
    }
        
    /**
     * Convierte este objeto Key a un predicado usando como valores de igualdad los valores actuales de las columnas.
     * 
     * @return Un objeto {@link Predicable} con las columnas que forman este objeto Key.
     */
    public Predicable toPredicate() { 
        return toPredicate(this.values());
        
    }
    
    
    /**
     * Convierte este objeto Key a un predicado usando como valores de igualdad los valores actuales de las columnas.
     * 
     * @param values    Un arreglo de  {@link Object} especificando los valores con los que se igualarán las columnas 
     * para formar este predicado.
     * 
     * @return          Un objeto {@link Predicable} con las columnas que forman este objeto Key.
     */
    
    public Predicable toPredicate(Object... values) {
        Predicable result = null;
        StringBuilder sb = new StringBuilder();
        Column pkCol;        
        for(int i = 0; i < this.elementCount; i++) {
            pkCol = (Column)this.elementData[i];
            if(i > 0) 
                sb.append(" and ");
            sb.append(pkCol.toString());            
            if(values[i] == null)
                sb.append(" IS NULL");
            else
                sb.append(" = ").append(JQLibHelper.str2var(values[i]));
        }
        if(sb.length() > 0)
            result = new Predicate(sb.toString());

        return result;
        
    }
}
