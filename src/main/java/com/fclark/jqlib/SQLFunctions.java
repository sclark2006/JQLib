package com.fclark.jqlib;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 24, 2011. Creación de clase SQLFunctions 
 *
 */
public class SQLFunctions {
    public static final SelectModifier DISTINCT = new SelectModifier("DISTINCT");
    
    //AGGREGATE FUNCTIONS
    public static Expression AVG(Object expr) {
        return JQLibHelper.func("AVG", expr);
    }
    
    public static Expression COUNT(Object expr) {
        return JQLibHelper.func("COUNT", expr);
    }
    
    public static Expression MAX(Object expr) {
        return JQLibHelper.func("MAX", expr);
    }
    
    public static Expression MIN(Object expr) {
        return JQLibHelper.func("MIN", expr);
    }
    
    public static Expression SUM(Object expr) {
        return JQLibHelper.func("AVG", expr);
    }    
}
