package com.fclark.jqlib;

/**
 * @author Frederick Clark
 * @version 0.1 Feb 08,2011 Creación de la interfaz Comparable
 * @version 0.2 Feb 10,2011 Adición de métodos greaterOrEqualsThan, lowerOrEqualsThan, in.
 *                          Adición de métodos sobrecargados que reciben un Object como argumento.
 *                          Adición de métodos isNull, isNotNull.
 *                          Cambio de nombre de la interfaz de Comparable a Predicate. 
 *                          Cambio de nombre y parámetros de equals(Predicate) a equalsTo(Object)
 *@version 0.3 Feb 16, 2011 Adición de método between(Object, Object)
 *@version 0.4 Mar 3, 2011. Adición de métodos leftOuterJoin() y rightOuterJoin()
 *@version 0.5 Mar 16, 2011. Adición de método in(Queryable<?>)
 *                          La interfaz Prediate se renombró a Predicable
 *                          Los métodos de la interfaz Predicable se movieron a la interfaz Operable.
 *                          Solo se dejaron los métodos and(Predicable) y or(Predicable) 
 */

public interface Predicable {
    
    Predicable and(Predicable predicate);
    
    Predicable or(Predicable predicate);
}
