package com.fclark.jqlib.oracle;

import com.fclark.jqlib.Expression;

/**
 * 
 * @author Frederick Clark
 * 
 * @version 0.1 Creación de Interfaz PLFunctions
 * @version 0.2 Adición de métodos rpad(), lpad(), replace(), substr(), instr()
 * @version 0.3 Mar 24, 2011. Movida al package com.unipago.jqlib.oracle. 
 *              Renombrada a OracleExpressions
 */

public interface OracleExpressions {
    Expression nvl(Object expr);
    //String functions
    Expression trim();
    Expression rtrim();
    Expression ltrim();
    Expression upper();
    Expression lower();
    Expression rpad(Object times, Object fill);
    Expression lpad(Object times, Object fill);
    Expression replace(Object search, Object replace);
    Expression replace(Object search);
    Expression substr(Object start, Object length);
    Expression instr(Object search, Object position);
    //Date functions
    Expression trunc();    
    Expression lastDay();
    Expression monthsBetween(Object val);
    Expression greatest(Object val);
    Expression least(Object val);
    //Number Functions
    Expression mod();
    Expression abs();
    Expression power(Object val);
    
    //Conversion Functions
    Expression toChar();
    Expression toChar(String format);
    Expression toDate();
    Expression toDate(String format);
    Expression toNumber();
    Expression toNumber(String format);
}
