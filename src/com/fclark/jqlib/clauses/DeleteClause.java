package com.fclark.jqlib.clauses;

import com.fclark.jqlib.DML;
import com.fclark.jqlib.Predicable;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 17, 2011. Creación de clase DeleteClause.  
 * Esta clase representa una clausula DELETE.
 */
public class DeleteClause extends ExecuteClause {

    public DeleteClause(DML creator) {
        super(creator);
        creator.getBuilder().append("delete from ").append(creator.getEntity().getName());
    }

    public ExecuteWhereClause where(Predicable predicate) {
        return new ExecuteWhereClause(creator, predicate);
    }  
}
