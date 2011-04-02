package com.fclark.jqlib;

/**
 * @author Frederick Clark
 * @version 0.1 Feb 08,2011 Creaci�n de la interfaz Comparable
 * @version 0.2 Feb 10,2011 Adici�n de m�todos greaterOrEqualsThan, lowerOrEqualsThan, in.
 *                          Adici�n de m�todos sobrecargados que reciben un Object como argumento.
 *                          Adici�n de m�todos isNull, isNotNull.
 *                          Cambio de nombre de la interfaz de Comparable a Predicate. 
 *                          Cambio de nombre y par�metros de equals(Predicate) a equalsTo(Object)
 *@version 0.3 Feb 16, 2011 Adici�n de m�todo between(Object, Object)
 *@version 0.4 Mar 3, 2011. Adici�n de m�todos leftOuterJoin() y rightOuterJoin()
 *@version 0.5 Mar 16, 2011. Adici�n de m�todo in(Queryable<?>)
 *                          La interfaz Prediate se renombr� a Predicable
 *                          Los m�todos de la interfaz Predicable se movieron a la interfaz Operable.
 *                          Solo se dejaron los m�todos and(Predicable) y or(Predicable) 
 */

public interface Predicable {
    
    Predicable and(Predicable predicate);
    
    Predicable or(Predicable predicate);
}
