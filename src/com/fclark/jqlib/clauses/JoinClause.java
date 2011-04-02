package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.Predicable;
import com.fclark.jqlib.Queryable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Feb 1, 2011. Creación de Interfaz.
 * @version 0.2 Mar 10, 2011. Renombrada a JoinClause y creada como clase. 
 */
public class JoinClause extends SQLClause {

    public static enum JoinType {
        INNER_JOIN, RIGHT_JOIN, LEFT_JOIN, JOIN;
        
        @Override
        public String toString() {
            return this.name().replace('_', ' ').toLowerCase();
        }
    }
    
    public JoinClause(Buildable creator, JoinType join, Entity<?> table) {
        super(creator);
        this.creator.getBuilder().append(' ').append(join.toString()).append(' ').append(table.toString());        

    }
    
    public Queryable<?> on(Predicable predicate) {
        creator.getBuilder().append(" on (").append(predicate.toString()).append(")\n");
        return creator.<Queryable<?>>getCreator() ;
    }
}
