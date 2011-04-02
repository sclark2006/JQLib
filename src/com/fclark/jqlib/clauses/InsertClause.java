package com.fclark.jqlib.clauses;

import com.fclark.jqlib.DML;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 14, 2011. Creación de clase InsertClause
 * @version 0.2 Mar 17, 2011. Se movieron los métodos values() y select() a la clase ValuesClause.
 *                            Ya no se implementan las interfaces Insertable e InsertableColumns. 
 * Esta clase representa una clausula INSERT, así como sus subclausulas (INTO, VALUES, etc.)
 * 
 */
public class InsertClause extends SQLClause  {

    public InsertClause(DML creator) {
        super(creator);
        this.creator.getBuilder().append("insert into ").append(creator.getEntity().getName()).append("\n");
    }

    //InsertableColumns

    public ValuesClause _(Column... columns) {
        return columns(columns);
    }

    public ValuesClause columns(Column... columns) {
        if(columns != null && columns.length > 0) {
            creator.setColumns(columns);
            creator.getBuilder().append("(").append(JQLibHelper.joinArray(columns)).append(")\n");
        }
        return new ValuesClause(creator);
    }    
}
