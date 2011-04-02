package com.fclark.jqlib;


/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 17, 2011. Creación de clase Predicate.
 *
 * Esta clase representa un predicado para condicionar los resultados de una consulta, o un comando de actualización o 
 *  eliminación. 
 */
public class Predicate extends Expression implements Predicable {

    public Predicate(String predicate) {
        super(predicate);
    }
    
    public Predicate(Predicable predicate) {
        super(predicate.toString());
    }
   
    public Predicable and(Predicable predicate) {
        this.name = "(".concat(this.name).concat(" and ").concat(JQLibHelper.testNull(predicate).toString()).concat(") ");
        return this;
    }
    
    public Predicable or(Predicable predicate) {
        this.name = "(".concat(this.name).concat(" or ").concat(JQLibHelper.testNull(predicate).toString()).concat(") ");
        return this;
    }
    
    public static Predicable exists(Resultable subQuery) {
        return new Predicate(" exists \n\t(" + subQuery.toString() + ")\n");
    }
    public static Predicable notExists(Resultable subQuery) {
        return new Predicate(" not exists \n\t(" + subQuery.toString() + ")\n");
    }
}
