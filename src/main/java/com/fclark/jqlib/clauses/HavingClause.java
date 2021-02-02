package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Predicable;


/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 10, 2011. Creación de Intefaz
 * @version 0.2 Mar 11, 2011. Cambio a Clase.
 */
public class HavingClause extends OrderClause {

    public HavingClause(Buildable creator) {
        super(creator);
    }
   
    public HavingConditionClause having(Predicable predicate) {
        this.creator.getBuilder().append("\n having ").append(predicate.toString());
        return new HavingConditionClause(this.creator);
    }
}
