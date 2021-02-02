package com.fclark.jqlib;

import com.fclark.jqlib.clauses.HavingClause;
import com.fclark.jqlib.clauses.JoinClause;
import com.fclark.jqlib.clauses.WhereClause;
import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Feb. 10,2011 Creación de Interfaz IQuery. 
 * @version 0.2 Mar 10,2011. Renombramiento de IQuery a Queryable<T>. Adición de métodos getAlias() y setAlias() 
 *                           existentes en la anterior interfaz Queryable. Hereda de GroupClause
 * @version 0.3 Mar 11, 2011. Se le agregaron los métodos groupBy() y orderBy(). Hereda Resultable.                         
 * @version 0.4 Mar 14, 2011. Extiende la interfaz Buildable. Se movieron los métodos setColumns() y 
 *                            getColumns() a la interfaz Buildable
 * @version 0.5 Mar 16, 2011. Adición de los métodos exists() y notExists(), que retornan un Predicate;
 * @version 0.6 Mar 18, 2011. Los métodos getAlias() y setAlias() se movieron a la interfaz Aliasable.
 *                            Implementación de interfaz Aliasable.
 */
public interface Queryable<T> extends Buildable, Resultable, Aliasable<T> {
    JoinClause innerJoin(Entity<?> table);
    JoinClause leftJoin(Entity<?> table);
    JoinClause rightJoin(Entity<?> table);
    WhereClause where(Predicable predicate);
    //WhereClause where(boolean cond);
    Predicable exists();
    Predicable notExists();
    Queryable<?> union(Query query);
    Queryable<?> unionAll(Query query);
    Queryable<?> minus(Query query);
    HavingClause groupBy(Column... columns);
    //HavingClause groupBy(String... columns);
    Resultable orderBy(Orderable... columns);
    Resultable orderBy(Integer... columns);
    //Resultable orderBy(String... columns);    
}
