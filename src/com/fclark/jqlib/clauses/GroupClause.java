package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.column.Column;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 10, 2011. Creación de Interfaz
 * @version 0.2 Mar 11, 2011. Cambiada a Clase.
 */
public class GroupClause extends OrderClause {
    public GroupClause(Buildable creator) {
        super(creator);
    }
    
    public HavingClause groupBy(Column... columns) {
        this.creator.getBuilder().append("\n group by ").append(JQLibHelper.joinArray(columns));
        return new HavingClause(this.creator);
    }   
}
