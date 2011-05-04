package com.fclark.jqlib;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.fclark.jqlib.clauses.DeleteClause;
import com.fclark.jqlib.clauses.InsertClause;
import com.fclark.jqlib.clauses.SelectClause;
import com.fclark.jqlib.clauses.UpdateClause;
import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.2 Feb 01st 2011 Creación de Clase
 * @version 0.3 Mar 11, 2011. Se quitó la implementación de Queryable.
 * @version 0.4 Mar 14, 2011. Se agregaron los métodos getName(), aliasedName()
 *          y insertInto().
 * @version 0.5 Mar 15, 2011. Se agregó la propiedad schema y el método
 *          insert();
 * @version 0.6 Mar 18, 2011. Implementación de interfaz Aliasable<T>
 * @version 0.7 Mar 21, 2011. Implementación de interfaz Updatable
 * @version 0.8 Mar 29, 2011. Se implementó el método setName(String)
 * @version 0.8 Mar 31, 2011. Se implementó el método delete().
 * @param <T>                   
 */

public abstract class Entity<T extends Entity<T>> implements Updatable<T> {
    
    private String alias;

    private String name;

    private String schema;
    
    private Column[] columns;
    
    private Key primaryKey;
    
    private Set<Assignable> changedColumns;

    public static final Column ALL = new Column("*");
    

    public Entity() {
        name = this.getClass().getSimpleName();
        alias = "";
        schema = "";
        changedColumns = new HashSet<Assignable>();
        primaryKey = new Key();
    }// Table()

    public Entity(String alias) {
        this();
        this.alias = alias;
        setSourceToColumns();
    }

    //private Methods
    private void setSourceToColumns() {
        for(Field field :this.getClass().getFields()) {
            try {
                ((Column)field.get(this)).setSource(this);
            } catch (Exception e) {
            }
        }//for
    }
    
    private boolean needsToBeSaved() {
        return !changedColumns.isEmpty();    
    }
    //public Methods
    
    //Aliasable
    @SuppressWarnings("unchecked")
    public T setAlias(String alias) {
        this.alias = alias;
        setSourceToColumns();
        return (T) this;
    }
    
    public String getAlias() {
        return this.alias;
    }    

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return this.schema;
    }   

    public void setName(String name) {
        if(name != null && !name.isEmpty())
            this.name = name;
    }
    
    public String getName() {
        if(schema != null && !schema.isEmpty())
            return schema + "." + name;
        else
            return name;
    }

    public String aliasedName() {
        return this.toString();
    }

    public String toString() {
        return getName() + (alias == null ? "" : " " + this.alias);
    }

    
    public InsertClause insertInto() {
        return new InsertClause(new DML(this));
    }
    
    //Updatable
    public void notifyValueChange(Assignable source) {
        changedColumns.add(source);
    }
   
    public Key getPrimaryKey() {
        return primaryKey;
    }
    
    @SuppressWarnings("unchecked")
    public Queryable<T> toQuery() {
        return (Queryable<T>) new SelectClause(new Query(), null, ALL).from(this);
    }

    public Column[] columns() {
        if(columns == null) {
            Field[] fields = this.getClass().getFields();
            columns = new Column[fields.length - 1];
            
            for (int i = 0; i < columns.length; i++) {
                if (Column.class.isAssignableFrom(fields[i].getType())
                        && !fields[i].getName().equals("ALL")) {
                    try {
                        columns[i] = (Column) fields[i].get(this);
                    } catch (Exception e) {
                        columns[i] = null;
                    }
                }
            }// for
        }
        return columns;
    }

    public Object[] values() {
        Object[] result = new Object[columns().length];
        for (int i = 0; i < result.length; i++) {
             result[i] = columns[i].get();
        }
        return result;
    }

    /**
     * Crea un nuevo registro en la base de datos en la entidad actual, de
     * acuerdo a los valores almacenados en cada campo
     * 
     * @return
     * @throws SQLException
     */
    public int insert() throws SQLException {
        int result = (new InsertClause(new DML(this))
                 .columns(this.columns())
                 .values(this.values())).execute();
        changedColumns.clear();
        return result;
    }
    
    public int insert(Object... values) throws SQLException {
        int result = 0;
        if(values != null && values.length == this.columns().length) {
            for (int i = 0; i < values.length; i++) {
                columns[i].set(values[i]);
            }
            result = insert();
        }
        return result;
    }

    public int update() throws SQLException {
        int result = new UpdateClause(new DML(this))
        .set((Assignable[])changedColumns.toArray())
        .where(primaryKey.toPredicate())
        .execute();
        changedColumns.clear();
        return result;
    }
    
    public int delete() throws SQLException {
        int result = new DeleteClause(new DML(this))
        .where(primaryKey.toPredicate())
        .execute();
        changedColumns.clear();
        return result;
    }
    
    public int save() throws SQLException {
        int result = 0;
        if(needsToBeSaved()) {
            if((result = this.update())== 0) {
                result = this.insert();
            }
        }
        
        return result;
    }

    
    public T find(Object... values) {
        return findFirstWhere(primaryKey.toPredicate(values));
    }
    
    public T findFirstBy(Column column, Object value) {
        return findFirstWhere(column.equal(value) );
    }
        
    @SuppressWarnings("unchecked")
    public T findFirstWhere(Predicable predicate) {
        try {
            return (T) toQuery().where(predicate).getFirst(this.getClass(), 
                    Resultable.FetchCriteria.ENTITY_COLUMNS, (Object[])null);
        } catch (Exception e) {
            return null;
        }
    }    
    
    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        try {
            return (Iterable<T>) toQuery().asIterable(this.getClass());
        } catch (Exception e) {
            return null;
        }
    }
       
    public Iterable<T> findBy(Column column, Object value) {
        return findWhere(column.equal(value));
    }
    
    @SuppressWarnings("unchecked")
    public Iterable<T> findWhere(Predicable predicate) {        
        try {
            return (Iterable<T>) toQuery().where(predicate).asIterable(this.getClass());
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public int count() {
        return count(this.getClass());
    }
    
    //Class Methods
    public static <U extends Entity<U>>  U findFirstWhere(Class<U> tableClass, Predicable predicate) {
        try {
            return toQuery(tableClass).where(predicate).getFirst(tableClass, 
                    Resultable.FetchCriteria.ENTITY_COLUMNS, (Object[])null);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static <U extends Entity<U>> Iterable<U> findAll(Class<U> eClass) {        
        try {
            return toQuery(eClass).asIterable(eClass);
        } catch (Exception e) {
            return null;
        }
    }
            
    public static <U extends Entity<U>>  U find(Class<U> tableClass, Object... values) {
        return newInstance(tableClass).find(values);
    }
    
    public static <U extends Entity<U>> U newInstance(Class<U> tableClass) {
        U ret = null;
        try {
            ret = tableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static <U extends Entity<U>> U alias(Class<U> tableClass,
            String alias) {
        return newInstance(tableClass).setAlias(alias);
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Entity<T>> Queryable<T> toQuery(Class<T> eClass) {
        return (Queryable<T>) new SelectClause(new Query(), null, ALL).from(eClass.getSimpleName());
    }
   
    public static <T extends Entity<T>> int count(Class<T> eClass) {
        try {
            return (new SelectClause(new Query(), null, ALL.count()).from(eClass.getSimpleName())).getSingleValue();
        } catch (Exception e) {
           return 0;
        } 
    }
    
}// class
