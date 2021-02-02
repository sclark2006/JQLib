package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Predicable;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 16, 2011. Creación de Clase
 * Esta clase representa las condiciones que se ponen a una sentencia DML como UPDATE o DELETE.
 */
public class ExecuteWhereClause extends ExecuteClause {

    public ExecuteWhereClause(Buildable creator, Predicable predicate) {
        super(creator);
        creator.getBuilder().append("\nwhere ").append(predicate.toString());
    }

    public ExecuteWhereClause and(Predicable predicate) {
        this.creator.getBuilder().append("\n and ").append(predicate.toString());
        return this;
    }
    
    public ExecuteWhereClause or(Predicable predicate) {
        this.creator.getBuilder().append("\n or ").append(predicate.toString());
        return this;
    } 
}
