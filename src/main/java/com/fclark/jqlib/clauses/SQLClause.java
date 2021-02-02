package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 11 Mar 2011. Creación de clase. 
 *          De esta clase heredan todas las clausulas SQL, como select, where, join, having, group by, etc.
 */
public abstract class SQLClause  {
    protected Buildable creator;
    
    public SQLClause(Buildable creator) {
        this.creator = creator;
    }
    
    
    public String toString() {
        return this.creator.getBuilder().toSQL();
    }
}
