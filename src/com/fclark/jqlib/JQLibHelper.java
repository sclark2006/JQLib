package com.fclark.jqlib;

import java.lang.reflect.Method;

import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Creación de Clase PLSF
 * @version 0.2 Mar 16, 2011. Creación de métodos str2var(Object,Method) y joinArray(Object[], boolean, Method);
 * @version 0.3 Mar 24, 2011. Renombrada a JQLibHelper
 * 
 * PL Standard Functions. Una colección de funciones comunes en PL/SQL
 * 
 */
public final class JQLibHelper {
    public static Object testNull(Object expr) {
        return expr == null ? "NULL" : expr;
    }
    public static String str2var(Object expr) {
        return (expr instanceof String || expr instanceof Character ? "'" + testNull(expr).toString() + "'" : testNull(expr)
                .toString());
    }

    public static String str2var(Object expr, Method method) {
        if(expr == null)
            return "NULL";
        else
        {
            String val;
            try {
                val = method.invoke(expr, (Object[]) null).toString();
            } catch (Exception e) {
                val = "";
            } 
            return expr instanceof String || expr instanceof Character ? "'" + val + "'" : val;
        }
    }
    
    public static StringBuilder joinArray(Object[] array) {
        return joinArray(array,true); 
    }
    
    public static StringBuilder joinArray(Object[] array, boolean detectString) {
        StringBuilder sbl = new StringBuilder();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                String val;
                 val = detectString ? str2var(array[i]) : testNull(array[i]).toString(); 
                sbl.append(i == 0 ? val : ","+ val);
            }
        }
        return sbl;
    }
    
    public static StringBuilder joinArray(Object[] array, boolean detectString, Method method) {
        StringBuilder sbl = new StringBuilder();
        if (array != null) {
            String val;
            for (int i = 0; i < array.length; i++) {
                try {
                    if(array[i] == null)
                        val = "NULL";
                    else
                        val = detectString ? str2var(array[i], method) : 
                                    method.invoke(array[i], (Object[]) null).toString();
                } catch (Exception e) {
                    val = "";
                }                
                sbl.append(i == 0 ? val : ","+ val);                
            }
        }
        return sbl;
    }
    
    public static Expression func(String fnName, Object... params) {
        return new Expression(fnName + "("+ JQLibHelper.joinArray(params,true) + ")");
    }
    
    public static Expression expr(Object val) {
        return new Expression(val.toString());
    }
    
    public static Column col(Object val) {
        return new Column(val.toString());
    }
}
