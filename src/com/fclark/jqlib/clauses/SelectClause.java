package com.fclark.jqlib.clauses;

import com.fclark.jqlib.Entity;
import com.fclark.jqlib.Expression;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.Query;
import com.fclark.jqlib.Queryable;
import com.fclark.jqlib.SelectModifier;
import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Feb 01,2011. Creación
 * @version 0.2 Cambio de tipo de retorno en métodos SQLQuery from() a IQuery
 * @version 0.3 Mar 10, 2011. Renombrada a SelectClause. Retorna Queryable<?>
 */
public class SelectClause extends SQLClause {

    public SelectClause(Queryable<?> creator,
            SelectModifier modifier, Expression... columns) {
        super(creator);
        if(columns == null || columns.length == 0)
        { 
            columns = new Expression[]{new Expression("*")};
        }        
        creator.setColumns(columns);
        creator.getBuilder().append("select ");
        if (modifier != null)
            creator.getBuilder().append(modifier.toString() + " ");
        creator.getBuilder().append(JQLibHelper.joinArray(columns)).append('\n');
    }

    public Queryable<?> from(Object... tables) {
        creator.getBuilder().append(" from ");
        creator.getBuilder().append(JQLibHelper.joinArray(tables, false));
        return creator.<Queryable<?>>getCreator();
    }
    
    
    public Queryable<?> from(Class<? extends Entity<?>> table) {
        Expression[] cols = creator.getColumns();
        String alias = "";
        if(cols.length > 0) {
            if(Column.class.isAssignableFrom(cols[0].getClass()))
                alias = ((Column)cols[0]).getSource().getAlias();
        }
        creator.getBuilder().append(" from ");
        creator.getBuilder().append(table.getSimpleName());
        if(!alias.isEmpty())
            creator.getBuilder().append(" ").append(alias);
            
        return creator.<Queryable<?>>getCreator();
    }

    public Queryable<?> from(Entity<?>... tables)  {
        return from((Object[])tables);
    }

    public Queryable<?> from(Query query) {
        return from( new Object[] { query.getBuilder().insert(0, '(').append(')') });
    }
}
