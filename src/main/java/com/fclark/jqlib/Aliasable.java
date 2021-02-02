package com.fclark.jqlib;

/**
 * 
 * @author Frederick Clark
 *
 * @version 0.1 Mar 18 2011. Creación de interfaz Aliasable
 */
public interface Aliasable<T> {
    String getAlias();
    T setAlias(String alias);
}
