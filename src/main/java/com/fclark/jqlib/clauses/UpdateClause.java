package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Assignable;
import com.fclark.jqlib.DML;

/**
 *  
 * @author Frederick Clark
 * @version 0.1 Mar 16, 2011. Creación de clase UpdateClause
 *  Esta clase representa una clausula UPDATE.
 *  
 */
public class UpdateClause extends SQLClause {

    public UpdateClause(DML creator) {
        super(creator);
        creator.getBuilder().append("update ").append(creator.getEntity().getName());
    }

    public SetClause set(Assignable... columns) {
        return new SetClause(creator, columns);
    }    
}
