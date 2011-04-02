package com.fclark.jqlib;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 09, 2011. Creación de la Interfaz. Para forzar a las columnas a que permitan almacenar un valor.
 *
 */
public interface Field {
    void set(Object value);
    Object get();
}
