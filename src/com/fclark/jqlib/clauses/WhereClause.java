package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Predicable;
import com.fclark.jqlib.Queryable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Feb 10, 2011. Creación interfaz Conditionable
 * @version 0.2 Mar 04, 2011. Adición de métodos groupBy()
 * @version 0.3 Mar 10, 2011. Renombrada a WhereClause. Hereda de la nueva interfaz GroupClause
 * @version 0.4 Mar 11, 2011. Convertida en una clase.
 */
public class WhereClause extends GroupClause {
    public WhereClause(Queryable<?> creator, Predicable predicate) {
        super(creator);
        this.creator.getBuilder().append("\n where ").append(predicate.toString());
    }
    
    public WhereClause and(Predicable predicate) {
        this.creator.getBuilder().append("\n and ").append(predicate.toString());
        return this;
    }
    public WhereClause or(Predicable predicate) {
        this.creator.getBuilder().append("\n or ").append(predicate.toString());
        return this;
    }
}