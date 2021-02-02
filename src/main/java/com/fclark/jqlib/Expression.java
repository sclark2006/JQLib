package com.fclark.jqlib;

import com.fclark.jqlib.column.Column;
import com.fclark.jqlib.oracle.OracleExpressions;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 17, 2011. Creación de la clase Expression
 * 
 * Esta clase es la superclase de toda columna, parámetro, cadena, número o expresión en general usada como columna, 
 * predicado en las clausulas sql tales como SELECT expr1[, expr2..], ORDER BY expr1[, expr2...], WHERE expr1 = expr2, etc.
 *
 */
public class Expression implements Operable, OracleExpressions {
    
    protected String name;
    
    public static final Expression NULL = new Expression("NULL");
    
    public Expression(String expr) { 
        this.name  = expr;
    }

    public Expression as(String alias) {
        if(alias != null && !alias.isEmpty())
            return new Column("(" + this.toString() + ") as "+ alias);
        else
            return this;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    //Operable
    public Predicable between(Object val1, Object val2) {
        return new Predicate(this.toString() + " between " + JQLibHelper.str2var(val1) + " and " + JQLibHelper.str2var(val2));
    }
    
    public Predicable equal(Object value) {
        return new Predicate(this.toString() + " = " + JQLibHelper.str2var(value) );        
    }
        
    public Predicable notEqual(Object value) {
        return new Predicate(this.toString() + " <> " + JQLibHelper.str2var(value));
    }
    
    public Predicable outerJoinLeft(Object value) {
        return new Predicate(this.toString() + "(+) = " + JQLibHelper.str2var(value) );
    }

    public Predicable outerJoinRight(Object value) {
        return new Predicate(this.toString() + " = " + JQLibHelper.str2var(value) + "(+)" );
    }   
    
    public Predicable isNull() {
        return new Predicate(this.toString() + " IS NULL");
    }

    public Predicable isNotNull() {
        return new Predicate(this.toString() + " IS NOT NULL");
    }    
    
    public Predicable like(Object value) {
        return new Predicate(this.toString() + " like " + JQLibHelper.str2var(value) );
    }

    public Predicable notLike(Object value) {
        return new Predicate(this.toString() + " not like " + JQLibHelper.str2var(value) );
    }
    
    public Predicable greaterThan(Object value) {
        return new Predicate(this.toString() + " > " + JQLibHelper.str2var(value) );
    }
    
    public Predicable lowerThan(Object value) {
        return new Predicate(this.toString() + " < " + JQLibHelper.str2var(value) );
    }
    
    public Predicable greaterOrEqualsThan(Object value) {
        return new Predicate(this.toString() + " >= " + JQLibHelper.str2var(value) );
    }

    public Predicable lowerOrEqualsThan(Object value) {
        return new Predicate(this.toString() + " <= " + JQLibHelper.str2var(value) );
    }    
        
    public Predicable in(Object... values) {
        return new Predicate(this.toString() + " in (" +  JQLibHelper.joinArray(values) + ")");
    }
    
    public Predicable in(Queryable<?> subQuery) {
        return new Predicate(this.toString() + " in (" +  subQuery.toString() + ")");
    }
     
    //Operators
    public Expression cat(Object val) {
        return new Expression(this.toString() + "||" + JQLibHelper.str2var(val));
    }

    //Math Operators
    public Expression div(Object val) {
        return new Expression(this.toString() + " / " + JQLibHelper.testNull(val).toString() );
    }

    public Expression minus(Object val) {
        return new Expression(this.toString() + " - " + JQLibHelper.testNull(val).toString() );
    }

    public Expression mul(Object val) {
        return new Expression(this.toString() + " * " + JQLibHelper.testNull(val).toString() );

    }

    public Expression plus(Object val) {
        return new Expression(this.toString() + " + " + JQLibHelper.testNull(val).toString() );
    }
    
    //PLFunctions
    public Expression nvl(Object expr) {
        return new Expression("nvl("+this.toString()+", " + JQLibHelper.str2var(expr)+ ")");
    }
    
    public Expression trunc() {
        return new Expression("trunc("+this.toString()+")");
    }
    
    public Expression trim() {
        return new Expression("trim("+this.toString()+")");
    }
    
    public Expression greatest(Object val) {
        return new Expression("greatest("+this.toString()+", " + JQLibHelper.str2var(val)+ ")");
    }
    
    public Expression least(Object val) {
        return new Expression("least("+this.toString()+", " + JQLibHelper.str2var(val)+ ")");
    }

    public Expression lastDay() {
        return new Expression("last_day("+this.toString()+")");
    }

    public Expression monthsBetween(Object val) {
        return new Expression("months_between("+this.toString()+", " + JQLibHelper.str2var(val)+ ")");
    }
    
    public Expression upper() {
        return new Expression("upper("+this.toString()+")");
    }

    public Expression lower() {
        return new Expression("lower("+this.toString()+")");
    }

    public Expression ltrim() {
        return new Expression("ltrim("+this.toString()+")");
    }

    public Expression rtrim() {
        return new Expression("rtrim("+this.toString()+")");
    }
    
    public Expression instr(Object search, Object position) {
        return new Expression("instr("+this.toString()+", " + 
                JQLibHelper.str2var(search)+ ", "+ position.toString() +")");
    }

    public Expression rpad(Object times, Object fill) {
        return new Expression("rpad("+this.toString()+", " + times.toString() + 
                ", "+ JQLibHelper.str2var(fill) +")");
    }

    public Expression lpad(Object times, Object fill) {
        return new Expression("lpad("+this.toString()+", " + times.toString() + 
                ", "+ JQLibHelper.str2var(fill) +")");
    }

    public Expression replace(Object search, Object replace) {
        return new Expression("replace("+ this.toString() + ", " + 
                          JQLibHelper.str2var(search) + ", "+ 
                          JQLibHelper.str2var(replace) +")");
    }

    public Expression replace(Object search) {
        return new Expression("replace("+ this.toString() + ", " + 
                JQLibHelper.str2var(search) +")");
    }


    public Expression substr(Object start, Object length) {
        return new Expression("substr("+ this.toString() + ", " + 
                start.toString() + ", " + 
                length.toString()+ ")");
    }

    public Expression abs() {
        return new Expression("abs("+this.toString()+")");
    }

    public Expression mod() {
        return new Expression("mod("+this.toString()+")"); 
    }
    
    public Expression power(Object val) {
        return new Expression("power("+this.toString()+", "+ val.toString()+")"); 
    }
    
    public Expression toChar(String format) {
        return new Expression("to_char(" + this.toString() + ", '" + JQLibHelper.testNull(format)  + "')");
    }

    public Expression toChar() {
        return new Expression("to_char(" + this.toString() + ")");
    }

    public Expression toDate() {
        return new Expression("to_date(" + this.toString() + ")");
    }
    
    public Expression toDate(String format) {
        return new Expression("to_date(" + this.toString() + ", '" + JQLibHelper.testNull(format)  + "')");
    }

    public Expression toNumber() {
        return new Expression("to_number(" + this.toString() + ")");
    }

    public Expression toNumber(String format) {
        return new Expression("to_number(" + this.toString() + ", '" + JQLibHelper.testNull(format) + "')");
    }
    
    //Aggregate
    public Expression avg() {
        return new Expression("avg("+ this.toString() + ")");
    }
    
    public Expression count() {
        return new Expression("count("+ this.toString() + ")");
    }

    public Expression max() {
        return new Expression("max("+ this.toString() + ")");
    }

    public Expression min() {
        return new Expression("min("+ this.toString() + ")");
    }

    public Expression sum() {
        return new Expression("sum("+ this.toString() + ")");
    }
    
    /*public Object val() {
        System.out.println(this);
        return this;
    }*/
}
