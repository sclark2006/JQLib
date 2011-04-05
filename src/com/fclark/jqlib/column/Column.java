package com.fclark.jqlib.column;

import java.sql.Types;
import java.util.Hashtable;

import com.fclark.jqlib.Assignable;
import com.fclark.jqlib.Expression;
import com.fclark.jqlib.Field;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.Orderable;
import com.fclark.jqlib.Updatable;


/**
 * @author Frederick Clark
 * @version 0.1 Feb 02,2011 Creaci�n de la clase Column
 * @version 0.4 Feb 09,2011 
 * @version 0.5 Feb 10,2011. Implementaci�n de interfaz Orderable v0.1 y cambios para incluir 
 *          nuevos m�todos de la interfaz Comparable v0.2
 * @version 0.6 Feb 16,2011. Implementaci�n de interfaz PLFunctions. 
 *                           Implementaci�n de interfaz PLOperators
 *           
 * @version 0.7   Mar 3, 2011. Implementaci�n de m�todos leftOuterJoin() y rightOuterJoin() de la interfaz Predicate.
 * @version 0.7.1 Mar 4, 2011. Implementaci�n de m�todos rpad(), lpad(), replace(), substr(), instr() de PLFunctions
 *                             Correcci�n al m�todo rightOuterJoin(); el s�mbolo (+) estaba en el lugar equivocado
 *                             Se cambi� el nombre de los m�todos leftOuterJoin() y rightOuterJoin() por los de 
 *                             outerJoinLeft() y outerJoinRight(), respectivamente.
 * @version 0.7.2 Mar 9, 2011. Se implementaron los m�todos de la interfaz com.unipago.jqlib.Field.
 * @version 0.7.3 Mar 16, 2011. Se implement� el m�todo in(Queryable<?>) de la interfaz Predicate.
 * @version 0.7.4 Mar 17, 2011. Se modific� para que hereda de la clase Expressi�n.
 *                              Se movieron los m�todos que implementan Operable y PLFunctions a la clase Expression.
 *                              Se implement� la interfaz Assignable. 
 *                              Se agreg� el m�todo to(Object) que retorna un Assignable. Este m�todo se utiliza para
 *                              asignar el valor de los campos en las clausulas UPDATE...SET.
 * @version 0.7.5 Mar 18, 2011. Se agreg� el campo boolean isPK, para indicar si un campo es Primary Key y getPkPosition  
 *                              Se agregaron los constructores Column(String, Aliasable) y Column(String, Aliasable, boolean)
 * @version 0.7.6 Mar 22, 2011. Se modificaron las firmas de los contructos creados en la versi�n 0.7.5 a 
 *                              Column(String, Updatable<?>) y Column(String,Updatable<?>,int);
 * @version 0.7.8 Mar 29, 2011. Se agreg� el Hashtable est�tico DATA_TYPES_MAP, donde se almacena una relaci�n entre los
 *                              tipos de datos SQL seg�n la tabla de constantes {@link java.sql.Types}.                              
 */
public class Column extends Expression implements Orderable, Field, Assignable {
    
    /**
     * {
     *  @code 
     *   DATA_TYPES_MAP.put(Types.VARCHAR, "VARCHAR");
     *   DATA_TYPES_MAP.put(Types.CHAR, "VARCHAR");
     *   DATA_TYPES_MAP.put(Types.NCHAR, "VARCHAR");
     *   DATA_TYPES_MAP.put(Types.NVARCHAR, "VARCHAR");
     *   DATA_TYPES_MAP.put(Types.TIME, "DATE");
     *   DATA_TYPES_MAP.put(Types.TIMESTAMP, "DATE");
     *   DATA_TYPES_MAP.put(Types.DATE, "DATE");
     *   DATA_TYPES_MAP.put(Types.NUMERIC, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.INTEGER, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.REAL, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.SMALLINT, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.BIT, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.FLOAT, "NUMBER");
     *   DATA_TYPES_MAP.put(Types.DOUBLE, "VARCHAR");
     *   }       
     */
    public static Hashtable<Integer, String> DATA_TYPES_MAP = new Hashtable<Integer, String>();  
    
    static {
        DATA_TYPES_MAP.put(Types.VARCHAR, "VARCHAR");
        DATA_TYPES_MAP.put(Types.CHAR, "VARCHAR");
        DATA_TYPES_MAP.put(Types.NCHAR, "VARCHAR");
        DATA_TYPES_MAP.put(Types.NVARCHAR, "VARCHAR");
        DATA_TYPES_MAP.put(Types.LONGNVARCHAR, "VARCHAR");
		DATA_TYPES_MAP.put(Types.LONGVARCHAR, "VARCHAR");
        DATA_TYPES_MAP.put(Types.TIME, "DATE");
        DATA_TYPES_MAP.put(Types.TIMESTAMP, "DATE");
        DATA_TYPES_MAP.put(Types.DATE, "DATE");
        DATA_TYPES_MAP.put(Types.NUMERIC, "NUMBER");
        DATA_TYPES_MAP.put(Types.INTEGER, "NUMBER");
        DATA_TYPES_MAP.put(Types.DECIMAL, "NUMBER");
        DATA_TYPES_MAP.put(Types.BIGINT, "NUMBER");
        DATA_TYPES_MAP.put(Types.REAL, "NUMBER");
        DATA_TYPES_MAP.put(Types.SMALLINT, "NUMBER");
        DATA_TYPES_MAP.put(Types.BIT, "NUMBER");
        DATA_TYPES_MAP.put(Types.FLOAT, "NUMBER");
        DATA_TYPES_MAP.put(Types.DOUBLE, "VARCHAR");
        
    }
    protected Updatable<?> source;
    protected Object value;
    protected int  pkPosition;
   
    public Column(String name) {
        this(name,null,0);
    }
    
    public Column(String name, Updatable<?> source) {
       this(name, source, 0);
    }
    
    public Column(String name, Object value) {
        this(name,null,0);
        this.value = value;
    }    
    
    public Column(String name, Updatable<?> source, int pkPos) {
        super(name);
        this.source = source;
        if(source != null && pkPos > 0) {
            this.source.getPrimaryKey().insertElementAt(this, pkPos - 1);
        }
        this.pkPosition = pkPos;
        this.value = null;
     }
    
    public void setSource(Updatable<?> source) {
        this.source = source;
    }
    
    public Updatable<?> getSource() {
        return this.source;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public boolean isPK() {
        return this.pkPosition > 0;
    }
    
    public int getPkPosition() {
        return this.pkPosition;
    }

    
    @Override
    public String toString() {
        return  source == null || source.getAlias().isEmpty()? this.name : source.getAlias() + "." + this.name;
    }    

    public Orderable asc() {
       return new Column(this.getName() + " asc");
    }

    public Orderable desc() {
        return new Column(this.getName() + " desc");
    }
    
    public Assignable to(Object value) {
        return new Column(this.toString() + " = " + JQLibHelper.str2var(value));
    }
    
    //Field
    public void set(Object value) {
        this.value = value;
        this.source.notifyValueChange(this);
    }
    
    public Object get() {
        return this.value;
    }
}

