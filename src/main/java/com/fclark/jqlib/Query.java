package com.fclark.jqlib;

import com.fclark.jqlib.clauses.GroupClause;
import com.fclark.jqlib.clauses.JoinClause;
import com.fclark.jqlib.clauses.SelectClause;
import com.fclark.jqlib.clauses.WhereClause;
import com.fclark.jqlib.column.Column;

/** 
 * Clase SQLQuery
 * @author Frederick Clark
 * @version 0.4 Feb 09, 2011 
 * @version 0.5 Feb 10, 2011 Ajustes por los cambios realizados a la clase Column.
 *                           Adición de constante SYSDATE,
 *                           Adición de método T getSingleValue() y into(Object[])
 *                           Adición de método boolean found(); Solo puede retornar un valor real después de invocar
 *                            los métodos getRecord() o into();
 *                            Renombrada a OraQuery
 * @version 0.6 Feb 16, 2011 Se movieron los métodos estáticos nvl y decode a la clase PLSF.
 *                           Se agregó la implementacion del método asQuery de la interfaz Resultable
 * @version 0.7 Feb 17, 2011 Se modificó el método estático select(OptimizerHint, Column...) para que reciba el nuevo tipo SelectModifier, 
 *                           en lugar del más específico OptimizerHint.
 *                           Se creó la constante DISTINCT, de tipo SelectModifier. 
 *                           Se creó la constante NULL, de tipo Column. 
 * @version 0.8 Mar 1, 2011  Se agregó el método estático select() (sin argumentos), que equivale a "SELECT * "                           
 *                           Se agregaron los métodos asList() y asIterable(), de la interfaz Resultable
 * @version 0.8.1 Mar 4, 2011. Se agregó el campo estático ALL que representa un * para realizar "SELECT *" o "COUNT(*)"
 * @version 0.8.2 Mar 10, 2011. Renombrada Query.
 * @version 0.8.3 Mar 11, 2011. Se movieron los campos específicos de Oracle a la clase OracleQuery.
 *                              Se dejó únicamente en esta clase la implementación de la interfaz Queryable, mientras que
 *                              el resto se movió a la clase GroupClause y sus superclases.
 * @version 0.9.0 Mar 15, 2011. Se crearon las clases NumericColumn, DateColumn y VarcharColumn, subclases de Column,
 *                              para futuros usos en las clases de entidades. 
 *                              Se creó la clase DML, donde se implementarán los métodos para insertar, actualizar y
 *                              eliminar datos de las tablas.
 * @version 0.9.1 Mar 16, 2011. Se agregaron los métodos exists() y notExists()       
 * @version 0.9.2 Mar 24, 2011. Se creó el método selectVar() para obtener valores de variables del RDBMS o ejecutar funciones
 *                              almacenadas. En ORACLE equivale a usar un select c[, c+1...] from dual.                                                     
 *  
 * @param <T>
 */
public class Query extends GroupClause implements Queryable<Query> {

    private static final long serialVersionUID = -6491834697180342888L;
    public static final Column ALL = new Column("*");
    
    private SQLBuilder queryBuilder;
    private Expression[] columns;
    private String alias;

    // Constructors
    public Query() {
        this(new SQLBuilder(""));
    }

    public Query(String query) {
        this(new SQLBuilder(query));
    }

    public Query(SQLBuilder query) {
        super(null);
        this.queryBuilder = query;
        super.conn = Environment.getConnection();
        super.creator = this;
    }
       
    public Query(String query, java.sql.Connection conn) {
        super(null);        
        this.queryBuilder = new SQLBuilder(query);
        this.conn = conn;
        if(Environment.getConnection() == null)
            Environment.setConnection(conn);
        super.creator = this;        
    }

    // Instance methods. 
    //Queryable
    public SQLBuilder getBuilder() {
        return this.queryBuilder;
    }
        
    public Queryable<Query> setColumns(Expression[] columns) {
        this.columns = columns;
        return this;
    }
    
    public Expression[] getColumns() {
        return this.columns;
    }
    
    public String getAlias() {
        return this.alias;
    }
    public Query setAlias(String alias) {
        this.alias = alias;
        return this;
    }
    
    public JoinClause innerJoin(Entity<?> table) {       
        return new JoinClause(this, JoinClause.JoinType.INNER_JOIN,table);
    }
    
    public JoinClause leftJoin(Entity<?> table) {       
        return new JoinClause(this, JoinClause.JoinType.LEFT_JOIN ,table);
    }
    
    public JoinClause rightJoin(Entity<?> table) {
        return new JoinClause(this, JoinClause.JoinType.RIGHT_JOIN,table);
    }
    
    public WhereClause where(Predicable predicate) {
        return new WhereClause(this, predicate);
    }
    
    public WhereClause where(boolean cond) {
        return new WhereClause(this, new Predicate(String.valueOf(cond)));
    }
    
    public Queryable<?> union(Query query) {
        this.queryBuilder.append("\n union \n").append(query.getBuilder().toSQL());
        return this;
    }
    
    public Queryable<?> minus(Query query) {
        this.queryBuilder.append("\n minus \n").append(query.getBuilder().toSQL());
        return this;
    }

    public Queryable<?> unionAll(Query query) {
        this.queryBuilder.append("\n union all \n").append(query.getBuilder().toSQL());
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public  <B extends Buildable> B getCreator() {
        return (B)this;
    }
        
    //static methods
    //Select
    public static synchronized SelectClause select() {
        return select(null, new Expression[] { new Expression("*")} );
    }

    public static synchronized SelectClause select(Expression... columns) {
        return select(null, columns);
    }
    
    public static synchronized SelectClause select(Object... columns) {
        Expression[] expr = new Expression[columns.length];        
        for(int i=0; i < expr.length; i++) {
            expr[i] = new Expression(columns[i].toString());
        }
        return select(null, expr);
    }

    public static synchronized SelectClause select(SelectModifier modifier, Expression... columns) {
        return new SelectClause(new Query(),modifier,columns);
    }
    
    /**
     * 
     * @param <T>       Tipo de dato a retornar, que puede ser {@link Object} o un objeto de la clase especificada 
     *                  por este genérico.
     * @param columns   Arreglo de expresiones retornadas desde el Database Engine.
     * @return          Un arreglo de {@link Object} o un objeto de la clase especificada por <T>, con los resultados
     *                  de la consulta.
     */
    @SuppressWarnings("unchecked")
    public static <T> T selectVar(Object... columns) {
        T result;        
        Queryable<?> query = select(columns).from("dual");
        try {
            if(columns.length > 1) {
                Object[] vals = new Object[columns.length];
                query.fetchInto(vals);                
                result = (T) vals;
            }
            else
            {
                result = query.getSingleValue();
            }
        }
        catch(Exception e)
        {
            result = null;
        }
        return result;
    }

}
