package com.fclark.jqlib;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 14, 2011. Creación de interfaz.
 *          Permite obtener un SQLBuilder 
 */
public interface Buildable {
    Buildable setColumns(Expression[] columns);
    Expression[] getColumns();
    SQLBuilder getBuilder();
    <B extends Buildable> B getCreator();
}
