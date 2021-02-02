package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.Orderable;
import com.fclark.jqlib.Predicable;
import com.fclark.jqlib.Predicate;
import com.fclark.jqlib.Resultable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 10, 2011. Creación de Interfaz
 * @version 0.2 Mar 11, 2011. Cambiada a Clase. Hereda de ExecuteClause
 * @version 0.3 Mar 23, 2011. Se crearon los métodos exists() y notExists().
 */
public class OrderClause extends ExecuteQueryClause {
    
    public OrderClause(Buildable creator) {
        super(creator);
    }    
    
    public Resultable orderBy(Orderable... columns) {
        this.creator.getBuilder().append("\n order by ").append(JQLibHelper.joinArray(columns));
        return this;
    }
    
    public Resultable orderBy(Integer... columns) {
        this.creator.getBuilder().append("\n order by ").append(JQLibHelper.joinArray(columns));
        return this;
    }    
    
    public Predicable exists() {
        return new Predicate(" exists \n\t(" + creator.getBuilder().toSQL() + ")\n");
    }
    public Predicable notExists() {
        return new Predicate(" not exists \n\t(" + creator.getBuilder().toSQL() + ")\n");
    }
}
