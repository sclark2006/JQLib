package com.fclark.jqlib.oracle;

import com.fclark.jqlib.Expression;
import com.fclark.jqlib.JQLibHelper;
/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 24, 2011. Creación de clase OracleFunctions
 *
 */
public class OracleFunctions {
    public static final Expression USER = new Expression("USER");
    public static final Expression ROWNUM = new Expression("ROWNUM");
    public static final Expression SYSDATE = new Expression("SYSDATE");
    
    //DECISION FUNCTIONS
    public static Expression NVL(Object val1, Object val2) {
        return JQLibHelper.func("NVL", val1, val2);
    }
    
    public static Expression DECODE(Object val, Object cmp, Object... values) {
        return new Expression("DECODE(" + JQLibHelper.str2var(val) + ", " + JQLibHelper.str2var(cmp) + ","
                + JQLibHelper.joinArray(values) + ")");
    }
    
    //CONVERSION FUNCTIONS
    public static Expression TO_CHAR(Object str) {
        return JQLibHelper.func("TO_CHAR", str);
    }
    
    public static Expression TO_CHAR(Object str, Object format) {
        return JQLibHelper.func("TO_CHAR", str, format);
    }
    
    public static Expression TO_NUMBER(Object str) {
        return JQLibHelper.func("TO_NUMBER", str);
    }
    
    public static Expression TO_NUMBER(Object str, Object format) {
        return JQLibHelper.func("TO_NUMBER", str, format);
    }
    
    public static Expression TO_DATE(Object str) {
        return JQLibHelper.func("TO_DATE", str);
    }
    
    public static Expression TO_DATE(Object str, Object format) {
        return JQLibHelper.func("TO_DATE", str, format);
    }
    //NUMERIC FUNCTIONS
    public static Expression ABS(Object val) {
        return JQLibHelper.func("ABS", val);
    }

    public static Expression ACOS(Object val) {
        return JQLibHelper.func("ACOS", val);
    }
    
    public static Expression ASIN(Object val) {
        return JQLibHelper.func("ASIN", val);
    }
    
    public static Expression ATAN(Object n) {
        return JQLibHelper.func("ATAN", n);
    }
    
    public static Expression ATAN2(Object x, Object y) {
        return JQLibHelper.func("ATAN2", x, y);
    }
    
    public static Expression CEIL(Object val) {
        return JQLibHelper.func("CEIL", val);
    }
    
    public static Expression COS(Object val) {
        return JQLibHelper.func("COS", val);
    }
    
    public static Expression EXP(Object val) {
        return JQLibHelper.func("EXP", val);
    }
    
    public static Expression FLOOR(Object val) {
        return JQLibHelper.func("FLOOR", val);
    }
    
    public static Expression GREATEST(Object val1, Object val2) {
        return JQLibHelper.func("GREATEST", val1, val2);
    }
    
    public static Expression LEAST(Object val1, Object val2) {
        return JQLibHelper.func("LEAST", val1, val2);
    }
    
    public static Expression LOG(Object val1, Object val2) {
        return JQLibHelper.func("LOG", val1, val2);
    }
        
    public static Expression MOD(Object val1, Object val2) {
        return JQLibHelper.func("MOD", val1, val2);        
    }
    
    public static Expression POWER(Object val1, Object val2) {
        return JQLibHelper.func("POWER", val1, val2); 
    }
    
    public static Expression ROUND(Object left) {
        return JQLibHelper.func("ROUND", left);
    }
    
    public static Expression ROUND(Object left, Object right) {
        return JQLibHelper.func("ROUND", left, right);
    }
    
    public static Expression SIGN(Object num) {
        return JQLibHelper.func("SIGN", num);
    }
    
    public static Expression SIN(Object num) {
        return JQLibHelper.func("SIN", num);
    }
    
    public static Expression SQRT(Object num) {
        return JQLibHelper.func("SQRT", num);
    }
    
    public static Expression TAN(Object num) {
        return JQLibHelper.func("TAN", num);
    }
    
    public static Expression TRUNC(Object num) {
        return JQLibHelper.func("TRUNC", num);
    }
    
    public static Expression TRUNC(Object num, Object places) {
        return JQLibHelper.func("TRUNC", num, places);
    }
    
        //TEXT FUNCTIONS
    
    public static Expression ASCII(Object chr) {
        return JQLibHelper.func("ASCII", chr);
    }
    
    public static Expression CHAR(Object ascii) {
        return JQLibHelper.func("CHAR", ascii);
    }

    public static Expression CONCAT(Object left, Object right) {
        return JQLibHelper.func("CONCAT", left, right);
    }
    
    public static Expression INSTR(Object str1, Object search) {
        return JQLibHelper.func("INSTR", str1, search);
    }
    
    public static Expression INSTR(Object str1, Object search, Object pos) {
        return JQLibHelper.func("INSTR", str1, search, pos);
    }

    public static Expression INSTR(Object str1, Object search, Object pos, Object nth) {
        return JQLibHelper.func("INSTR", str1, search, pos, nth);
    }
    
    public static Expression LOWER(Object val) {
        return JQLibHelper.func("LOWER", val);
    }

    public static Expression LTRIM(Object val) {
        return JQLibHelper.func("LTRIM", val);
    }

    public static Expression RTRIM(Object val) {
        return JQLibHelper.func("RTRIM", val);
    }    
  
    public static Expression REPLACE(Object val, Object search) {
        return JQLibHelper.func("REPLACE", val, search);
    }
    
    public static Expression REPLACE(Object val, Object search, Object replace) {
        return JQLibHelper.func("REPLACE", val, search, replace);
    }
        
    public static Expression SOUNDEX(Object val) {
        return JQLibHelper.func("SOUNDEX", val);
    }
    
    public static Expression SUBSTR(Object val, Object pos1) {
        return JQLibHelper.func("SUBSTR", val, pos1);
    }
    
    public static Expression SUBSTR(Object val, Object pos1, Object len) {
        return JQLibHelper.func("SUBSTR", val, pos1, len);
    }
    
    public static Expression TRIM(Object val) {
        return JQLibHelper.func("TRIM", val);
    }
        
    public static Expression UPPER(Object val) {
        return JQLibHelper.func("UPPER", val);
    }

    //DATE TIME FUNCTIONS    
       
    public static Expression LAST_DAY(Object val) {
        return JQLibHelper.func("LAST_DAY", val);
    }

    public static Expression MONTHS_BETWEEN(Object val1, Object val2) {
        return JQLibHelper.func("MONTHS_BETWEEN", val1, val2);
    }
        
    //DBMS SYSTEM FUNCTIONS
    public static Expression SYS_CONTEXT(String nameSpace, String parameter) {
        return JQLibHelper.func("SYS_CONTEXT", nameSpace, parameter);
    }
    
}
