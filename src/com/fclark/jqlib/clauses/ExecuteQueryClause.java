package com.fclark.jqlib.clauses;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.Expression;
import com.fclark.jqlib.Queryable;
import com.fclark.jqlib.Resultable;
import com.fclark.jqlib.SQLClauseException;
import com.fclark.jqlib.column.Column;


/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 16, 2011. Creación de la clase ExecuteQueryClause. 
 *                            A esta clase se movio desde la clase ExecuteClause la implementación de la interfaz Resultable
 *                            para mantener separadas la abstracción de la ejecución de un consulta de la ejecución de un
 *                            comando DML (insert, update, delete).
 *                             Están pendiente de implementación los métodos asList().
 * @version 0.2 Apr 1, 2011.  Corrección de un bug en el método getJavaFromSQL() cuando el número retornado desde una columna era muy grande.  
 */

public class ExecuteQueryClause extends ExecuteClause  implements Resultable {
    
    private boolean found;
    private static final Map<Class<?>, Class<?>> WRAPPERS = new java.util.HashMap<Class<?>, Class<?>>();
    
    // Initializers
    static {
        WRAPPERS.put(byte.class, Byte.class);
        WRAPPERS.put(short.class, Short.class);
        WRAPPERS.put(char.class, Character.class);
        WRAPPERS.put(int.class, Integer.class);
        WRAPPERS.put(long.class, Long.class);
        WRAPPERS.put(float.class, Float.class);
        WRAPPERS.put(double.class, Double.class);
        WRAPPERS.put(boolean.class, Boolean.class);
    }

    //Constructors
    public ExecuteQueryClause(Buildable creator) {
        super(creator);
    }
        
    //private Methods
    private Object wrapPrimitive(Class<?> type, String value) throws Exception {
        return WRAPPERS.get(type).getMethod("valueOf", String.class).invoke(
                null, value);
    }

    /*private Object getCastedValue(Class<?> type, ResultSet rs, Object param)
            throws Exception {
        String methodName = type.getSimpleName();
        methodName = "get" + methodName.substring(0, 1).toUpperCase()
                + methodName.substring(1);
        return ResultSet.class.getMethod(methodName, int.class).invoke(rs,
                param);
    }*/
    
    private void setFromJavaType(Class<?> fieldType, ResultSet res, int colPos, Object value)
    throws SQLException {
        try {
            if (fieldType.equals(String.class))
                res.updateString(colPos, value.toString());
            else if (fieldType.equals(boolean.class))
                res.updateBoolean(colPos,Boolean.class.cast(value).booleanValue());
            else if (fieldType.equals(double.class))
                res.updateDouble(colPos,Double.class.cast(value).doubleValue());
            else if (fieldType.equals(float.class))
                res.updateFloat(colPos,Float.class.cast(value).floatValue());
            else if (fieldType.equals(short.class))
                res.updateShort(colPos,Short.class.cast(value).shortValue());
            else if (fieldType.equals(java.util.Date.class))
                res.updateDate(colPos,new java.sql.Date(((java.util.Date) value).getTime()));
            else if (fieldType.equals(int.class))
                res.updateInt(colPos, Integer.class.cast(value).intValue());
            else if (fieldType.equals(long.class))
                res.updateLong(colPos, Long.class.cast(value).longValue());
            else if (fieldType.equals(byte.class))
                res.updateByte(colPos, Byte.class.cast(value).byteValue());
            else
                res.updateObject(colPos, value);
        
        } catch (IllegalArgumentException ia) {
            throw new SQLException("setJavaObject: Cannot map a "
                    + (res.getMetaData().getColumnClassName(colPos) 
                            + " type to a " + fieldType.getName()));
        }
        
    }   

    private Object getFromJavaType(Class<?> fieldType, ResultSet res, int colPos)
            throws SQLException {
        Object result;
        try {
            if (fieldType.equals(String.class))
                result = res.getString(colPos);
            else if (fieldType.equals(boolean.class))
                result = res.getBoolean(colPos);
            else if (fieldType.equals(double.class))
                result = res.getDouble(colPos);
            else if (fieldType.equals(float.class))
                result = res.getFloat(colPos);
            else if (fieldType.equals(short.class))
                result = res.getShort(colPos);
            else if (fieldType.equals(java.util.Date.class))
                result = res.getDate(colPos);
            else if (fieldType.equals(int.class))
                result = res.getInt(colPos);
            else if (fieldType.equals(long.class))
                result = res.getLong(colPos);
            else if (fieldType.equals(byte.class))
                result = res.getByte(colPos);
            else
                // if (fieldType.equals(Object.class))
                result = res.getObject(colPos);

            return result;
        } catch (IllegalArgumentException ia) {
            throw new SQLException("getJavaObject: Cannot map a "
                    + (res.getObject(colPos).getClass().getName()
                            + " type to a " + fieldType.getName()));
        }

    }

    private Object getFromSQLType(Class<?> fieldType, ResultSet res, int colPos)
            throws Exception {
        Object result = null;
        int colType = res.getMetaData().getColumnType(colPos);
        if (fieldType.equals(String.class)) {
            result = res.getString(colPos);
        } else {
            switch (colType) {
            case Types.INTEGER:
                result = res.getInt(colPos);
                break;
            case Types.BIGINT:
                result = res.getLong(colPos);
                break;
            case Types.DECIMAL:
            case Types.FLOAT:
                result = res.getFloat(colPos);
                break;
            case Types.NULL:
                break;                
            case Types.REAL:
            case Types.DOUBLE:
                result = res.getDouble(colPos);
                break;
            case Types.NUMERIC:
                if(fieldType.isPrimitive())
                    //result = getCastedValue(fieldType, res, colPos); //FC. Apr 08, 2011.
                    result = getFromJavaType(fieldType, res, colPos); //FC. Apr 08, 2011.
                else {
                    if (res.getMetaData().getScale(colPos) > 0)
                        result = res.getDouble(colPos);
                    else
                        result = res.getLong(colPos); // res.getInt(colPos); Esto daba un error cuando el número era muy grande. FC. Apr 1, 2011.
                }                
                break;
            case Types.CHAR:
                if (fieldType.equals(boolean.class)) {
                    switch (res.getString(colPos).charAt(0)) {
                    case '1':
                    case 'Y':
                    case 'T':
                    case 'S':
                    case 'y':
                    case 't':
                    case 's':
                        result = true;
                        break;
                    default:
                        result = false;
                        break;
                    }
                } else if (fieldType.equals(char.class))
                    result = res.getString(colPos).charAt(0);
                else
                    result = res.getString(colPos);
                break;
            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
            case Types.NVARCHAR:
            case Types.NCHAR:
            case Types.VARCHAR:
                result = res.getString(colPos);
                if (fieldType.equals(boolean.class))
                    result = res.getString(colPos).equals("true") ? true
                            : false;
                else
                    result = res.getString(colPos);
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                result = res.getDate(colPos);
                break;
            case Types.BIT:
                result = res.getBoolean(colPos);
                break;

            default:
                result = res.getObject(colPos);
                break;
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private <T>  T createObjectFromResult(Class<T> type, FetchCriteria fc, ResultSet res) throws Exception {
        T record = null;
        ResultSetMetaData md = res.getMetaData();
        if(found) {
            if (md.getColumnCount() == 1) { // Si se está consultando sólo una columna
                if(type == null)
                    type = (Class<T>) Object.class;   
                
                switch(fc) {
                    case OBJECT_PROPERTIES: 
                         record = (T)getFromJavaType(type,res,1);     //FC. Apr 08, 2011.
                        //record = (T) getCastedValue(type, res, 1); // FC. Apr 08, 2011. 
                        break;
                    case RESULTSET_COLUMNS:
                        record = (T) getFromSQLType(type, res, 1);
                        break;
                }
                
                if (res.wasNull())
                    record = null;
                
            } else { // Si se están consultando más de una columna
                Class<?> fieldType = Object.class;
                if(type == null)
                {
                    record = (T) getFromSQLType(fieldType, res, 1);
                }
                else
                {
                    Object value = null;
                    record = type.newInstance();
                    switch (fc) {
                        case RESULTSET_COLUMNS:
                            for (int i = 0; i < md.getColumnCount(); i++) {
                                value = getFromSQLType(fieldType, res, i + 1);
                            }
                            break;
                        case ENTITY_COLUMNS:
                            Column[] cols = Entity.class.cast(record).columns();
                            //Si la entidad tiene la misma cantidad de columnas que el query resultante
                            //llena la entidad por orden de los campos
                            if(md.getColumnCount() == cols.length) {
                                for (int i = 0; i < cols.length; i++) {
                                    value = getFromSQLType(fieldType, res, i + 1);
                                    cols[i].set(value);
                                }//for
                            }
                            else //De lo contrario, busca las columnas por nombre. Este método es más lento que el anterior
                            {
                                int foundCols = 0;
                                for (int i = 0; i < md.getColumnCount(); i++) {
                                    //Busca los campos de la clase entity por nombre
                                    Column column;
                                    try {
                                        Field field = type.getField(md.getColumnName(i + 1));
                                        column = Column.class.cast(field.get(record));
                                        foundCols++;
                                        value = getFromSQLType(fieldType, res, i + 1);
                                        column.set(value);
                                    }
                                    catch(Exception e) {
                                        column = null;
                                    }
                                }//for
                                
                                if(foundCols == 0 ) { // Si no se encontró por lo menos una columna
                                    for (int i = 0; i < cols.length && i < md.getColumnCount(); i++) {
                                        value = getFromSQLType(fieldType, res, i + 1);
                                        cols[i].set(value);
                                    }//for
                                }//if
                            }//else
        
                            break;
                        case OBJECT_PROPERTIES:
                            //Invoca los setters de la clase según el orden de los métodos y los campos de la Entidad.
                            Method[] methods = methodsNamed(type.getMethods(), "set"); //Obtiene todos los setters
                            for (int i = 0; i < md.getColumnCount() && i < methods.length; i++) {
                                fieldType = methods[i].getParameterTypes()[0];
                                value = getFromJavaType(fieldType, res, i + 1);
                                methods[i].invoke(record, value);                        
                            }
                            break;
                        //default:
                        //    value = getJavaObject(fieldType, res, i + 1);
                        //    break;
                    }//switch
                }//type == null
            }//if Column count == 1 
        }//found
        
        if (record == null && md.getColumnCount() == 1) {
            record = (T) wrapPrimitive(int.class, "-1");
        }
        
        return record;
    }
    
    
    //Public Methods
   
    // Resultable
    public Expression[] getColumns() {
        return this.creator.getColumns();
    }

    public void close() {
        try {
            statement.close();
        } catch (Exception e) {
        }
    }

    public ResultSet asResultSet() throws SQLException {
        return asResultSet((Object[]) null);
    }

    public ResultSet asResultSet(Connection conn) throws SQLException {
        this.conn = conn;
        return asResultSet();
    }

    public ResultSet asResultSet(Connection conn, Object... params)
            throws SQLException {
        this.conn = conn;
        return asResultSet(params);
    }

    public ResultSet asResultSet(Object... params) throws SQLException {
        ResultSet rs = null;
        try {
            if (statement == null)
                statement = this.conn.prepareStatement(this.creator.getBuilder()
                        .toSQL());
            else
                statement.clearParameters();

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    setJavaObject(params[i], statement, i + 1);
                }
            }

            rs = statement.executeQuery();
        } catch (NullPointerException npe) {
            throw new SQLException("Database connection needed");
        }

        return rs;
    }

    public void fetchInto(Object[] columns) throws Exception {
        fetchInto(columns, (Object[]) null);
    }

    public void fetchInto(Object[] columns, Object... params) throws Exception {
        found = false;
        ResultSet res = asResultSet(params);
        int cols = res.getMetaData().getColumnCount();

        if (columns == null || columns.length == 0)
            columns = (Object[]) Array.newInstance(Object.class, cols);

        if (columns.length < cols)
            throw new IllegalArgumentException(
                    "The destination array must have at least the same size of the column count to be retrieved");
        found = res.next();
        if (found) {
            for (int i = 0; i < cols; i++)
                columns[i] = getFromSQLType(columns.getClass()
                        .getComponentType(), res, i + 1);
        }
        if (res != null) {
            res.close();
            res = null;
        }
    }

    public <T> T getSingleValue() throws Exception {
        return getSingleValue((Object[]) null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleValue(Object... params) throws Exception {
        return (T) getFirst(null, FetchCriteria.RESULTSET_COLUMNS,
                params);
    }

    public <T> T getSingleValue(Class<T> type, Object... params)
            throws Exception {
        return (T) getFirst(type, FetchCriteria.RESULTSET_COLUMNS, params);
    }

    public <T> T getFirst(Class<T> type) throws Exception {
        return getFirst(type, FetchCriteria.OBJECT_PROPERTIES, (Object[]) null);
    }

    public <T> T getFirst(Class<T> type, Object... params) throws Exception {
        return getFirst(type, FetchCriteria.OBJECT_PROPERTIES, params);
    }

    public <T> T getFirst(Class<T> type, FetchCriteria fetchCriteria,
            Object... params) throws Exception {
        T resp = null;
        ResultSet res = asResultSet(params);
        found = res.next();
        resp = createObjectFromResult(type, fetchCriteria, res);
        if (res != null) {
            res.close();
            res = null;
        }
        return resp;
    }//getFirst

    public boolean found() {
        return found;
    }

    public <T extends Entity<T>> Iterable<T> asIterable(final Class<T> type) throws Exception {
        return asIterable(type,(Object[]) null);
    }

    public <T extends Entity<T>> Iterable<T> asIterable(final Class<T> type, Object... params)
            throws Exception {
        final ResultSet res = asResultSet(params);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new ResultIterator<T>(res, type);
            }

        }; // iterable
    }
    
    public <T extends Entity<T>> List<T> asList(Class<T> type) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public <T extends Entity<T>> List<T> asList(Class<T> type, Object... params)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Queryable<?> toQuery() throws SQLClauseException {
        return creator.<Queryable<?>> getCreator();
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return this.conn;
    }    

    // Class methods
    private static Method[] methodsNamed(Method[] methods, String name) {
        ArrayList<Method> ls = new ArrayList<Method>();
        Method[] result = new Method[0];
        for (Method m : methods)
            if (m.getName().startsWith(name))
                ls.add(m);

        return ls.toArray(result);
    }

    private class ResultList<E> extends AbstractList<E> {
        private ResultSet resultSet;
        private Class<E> klass;
        
        public ResultList(ResultSet resultSet, Class<E> klass) {
            this.resultSet = resultSet;
            this.klass = klass;
        }

        @Override
        public E get(int index) {
            E result;
            try {
                if(!resultSet.isClosed() && 
                    resultSet.getType() == ResultSet.TYPE_SCROLL_INSENSITIVE &&
                    resultSet.absolute(index+1) ) {
                        result =  createObjectFromResult(klass, FetchCriteria.ENTITY_COLUMNS, resultSet);
                }
                else
                    result = null;
            }
            catch(Exception e) {
                e.printStackTrace();
                result = null;
            }                        
            
            return result;
        }

        @Override
        public int size() {
            int result;
            try {
                if(resultSet.last())
                    result = resultSet.getRow();
                else                
                    result = 0;
                }
            catch(Exception e) {
                e.printStackTrace();
                result = 0;
            }            
            return result;
        }
        
        @Override
        public void add(int index, E element) {
            //this.resultSet
            //this.resultSet.insertRow();
        }
        
        @Override
        public E set(int index, E element) {
            return null;            
        }
        
        @Override
        public E remove(int index) {
            return null;
        } 

         
    }//class ResultList
    
    
    private class ResultIterator<R> implements Iterator<R> {
        private ResultSet rs;
        private Class<R> rType;
        public ResultIterator(ResultSet res, Class<R> recType) {
            this.rs = res;
            this.rType = recType;
        }
        public boolean hasNext() {
            try {
                found = rs.next();
                if(!found)
                    rs.close();
            } catch (SQLException sqle) {
                // e.printStackTrace();
                found = false;
                try {
                    rs.close();
                }
                catch(Exception e) {}
            }

            return found;
        }

        public R next() {
            R result = null;
            try {
                if(!rs.isClosed() ) {
                    result =  createObjectFromResult(rType, FetchCriteria.ENTITY_COLUMNS, rs);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                result = null;
            }                        
            
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}