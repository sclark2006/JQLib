package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Assignable;
import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.Predicable;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 16, 2011. Creaci�n de clase SetClause.
 * 
 * Apoya la construcci�n de una clausula UPDATE agregando la subclausulas SET y las condiciones de actualizaci�n 
 * mediante la clausula WHERE  
 *
 */
public class SetClause extends ExecuteClause {

    public SetClause(Buildable creator, Assignable[] columns) {
        super(creator);
        creator.getBuilder().append("\nset ").append(JQLibHelper.joinArray(columns));
    }
    
    public ExecuteWhereClause where(Predicable predicate) {
        return new ExecuteWhereClause(creator, predicate);
    }    

}
