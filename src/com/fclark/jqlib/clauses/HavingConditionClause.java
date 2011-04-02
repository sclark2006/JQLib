package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Predicable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 10, 2011. Creación de Interfaz
 * @version 0.2 Mar 11, 2011. Cambio a clase.
 */
public class HavingConditionClause extends OrderClause {
    
    public HavingConditionClause(Buildable creator) {
        super(creator);
    }
    
    public HavingConditionClause and(Predicable predicate) {
        this.creator.getBuilder().append("\n and ").append(predicate.toString());
        return this;
    }
    
    public HavingConditionClause or(Predicable predicate) {
        this.creator.getBuilder().append("\n or ").append(predicate.toString());
        return this;
    }
}
