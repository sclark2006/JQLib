package com.fclark.jqlib;

import java.sql.SQLException;

import com.fclark.jqlib.clauses.DeleteClause;
import com.fclark.jqlib.clauses.InsertClause;
import com.fclark.jqlib.clauses.UpdateClause;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 14, 2011. Creación de clase DML, con métodos insertInto(Entity)  
 * @version 0.2 Mar 16, 2011. Implementación de métodos update(Entity)
 * @version 0.3 Mar 17, 2011. Implementación de métodos deleteFrom(Entity)
 */
public class DML implements Buildable {
    private SQLBuilder builder;
    private Expression[] columns;
    private Entity<?> entity;
    
    public DML(Entity<?> table) {
        this(table, new SQLBuilder(""));
    }
    
    
    public DML(Entity<?> table, String query) {
        this(table, new SQLBuilder(query));
    }

    public DML(Entity<?> table, SQLBuilder query) {
        this.entity = table;
        this.builder = query;
    }

    //Buildable
    public SQLBuilder getBuilder() {
        return this.builder;
    }


    public Expression[] getColumns() {
        return this.columns;
    }
    
    public DML setColumns(Expression[] columns) {
        this.columns = columns;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DML getCreator() {
        return this;
    }

    public Entity<?> getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }

    //Insert  
    public static synchronized InsertClause insertInto(Entity<?> table) {
        return new InsertClause(new DML(table));
    }
    
    public static synchronized InsertClause insertInto(Class<? extends Entity<?>> table) 
       throws SQLClauseException {
            try {
                return new InsertClause(new DML(table.newInstance()));
            } catch (InstantiationException e) {
                throw new SQLClauseException("You can't instantiate the specified entity class: "+ 
                        table.getSimpleName()+ " because "+ e.getMessage());
            } catch (IllegalAccessException e) {
                throw new SQLClauseException("You have not access to the specified entity class: "+ 
                        table.getSimpleName()+ "because "+ e.getMessage());
            }
    }
    
    public static synchronized UpdateClause update(Entity<?> table) {
        return new UpdateClause(new DML(table));
    }
    
    public static synchronized UpdateClause update(Class<? extends Entity<?>> table) 
    throws SQLClauseException {
         try {
             return new UpdateClause(new DML(table.newInstance()));
         } catch (InstantiationException e) {
             throw new SQLClauseException("You can't instantiate the specified entity class: "+ 
                     table.getSimpleName()+ " because "+ e.getMessage());
         } catch (IllegalAccessException e) {
             throw new SQLClauseException("You have not access to the specified entity class: "+ 
                     table.getSimpleName()+ "because "+ e.getMessage());
         }
    }
    
    public static synchronized DeleteClause deleteFrom(Entity<?> table) {
        return new DeleteClause(new DML(table));
    }
    
    public static synchronized DeleteClause deleteFrom(Class<? extends Entity<?>> table) 
    throws SQLClauseException {
         try {
             return new DeleteClause(new DML(table.newInstance()));
         } catch (InstantiationException e) {
             throw new SQLClauseException("You can't instantiate the specified entity class: "+ 
                     table.getSimpleName()+ " because "+ e.getMessage());
         } catch (IllegalAccessException e) {
             throw new SQLClauseException("You have not access to the specified entity class: "+ 
                     table.getSimpleName()+ "because "+ e.getMessage());
         }
    }    
    
    public static synchronized void commit() throws SQLException {
        Environment.getConnection().commit();
    }
    
}
