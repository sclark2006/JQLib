package com.fclark.jqlib;
/**
 * 
 * @author Frederick Clark
 * 
 * @version 0.1 Feb.16 2011. Creación de Interfaz PLOperators
 * @version 0.2 Mar 16, 2011. Renombrada a Operable
 *              
 */

public interface Operable {
    //Math
    Expression plus(Object val);
    Expression minus(Object val);
    Expression mul(Object val);
    Expression div(Object val);
    //String
    Expression cat(Object val);
    
    //Logical /Comparison
    Predicable equal(Object col);
    
    Predicable notEqual(Object col);
    
    Predicable isNull();
    
    Predicable isNotNull();
        
    Predicable like(Object col);
       
    Predicable notLike(Object col);
       
    Predicable greaterThan(Object col);
    
    Predicable lowerThan(Object col);
        
    Predicable greaterOrEqualsThan(Object col);
    
    Predicable lowerOrEqualsThan(Object col);
    
    Predicable in(Object... values);
    
    Predicable in(Queryable<?> subQuery);
    
    Predicable between(Object val1, Object val2 );
    
    //Join
    Predicable outerJoinLeft(Object column);

    Predicable outerJoinRight(Object column);
}
