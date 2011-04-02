package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Executable;
import com.fclark.jqlib.Expression;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.SQLClauseException;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 17, 2011. Creación de ValuesClause
 * Representa una cláusula VALUES (...) o un subquery utilizado para alimentar un INSERT statement
 *
 */
public class ValuesClause extends SQLClause {

    public ValuesClause(Buildable creator) {
        super(creator);
    }
    
    public Executable values(Object... values) {
        creator.getBuilder().append(" values(" + JQLibHelper.joinArray(values, true)+")");
        return new ExecuteClause(creator);
    }
    
    public SelectClause select(Expression... columns) throws SQLClauseException {
        //return new SelectClause(new Query(creator.getBuilder()),null,columns);
        return new SelectClause(creator.getBuilder().createQuery() ,null,columns);
    }

    public SelectClause select() throws SQLClauseException {
        return new SelectClause(creator.getBuilder().createQuery(),null,(Expression[])null);
    }


}
