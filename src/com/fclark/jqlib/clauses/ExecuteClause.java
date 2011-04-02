package com.fclark.jqlib.clauses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.fclark.jqlib.Buildable;
import com.fclark.jqlib.Environment;
import com.fclark.jqlib.Executable;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 Mar 10, 2011. Creación de clase ExecuteClause A esta clase se
 *          movieron los campos de OraQuery que implementan la interfaz
 *          Resultable.
 * @version 0.2 Mar 14, 2011. Implementación de la interfaz Executable
 * @version 0.4 Mar 15, 2011. Implementación de métodos asIterable()
 * @version 0.5 Mar 16, 2011. Se eliminó la implementación de la interfaz Resultable.
 *                            Los métodos que implementan esta interfaz se movieron a la nueva clase ExecuteQueryClause
 */
public class ExecuteClause extends SQLClause implements Executable {

    protected PreparedStatement statement;
    protected Connection conn;
    private boolean wasFound;

    // Constructors
    public ExecuteClause(Buildable creator) {
        super(creator);
        conn = Environment.getConnection();
    }

    protected void setJavaObject(Object value, PreparedStatement ps, int colPos)
            throws SQLException {
        if (value == null) {
            ps.setNull(colPos, Types.NULL);
        } else if (value instanceof String) {
            ps.setString(colPos, (String) value);
        } else if (value instanceof java.util.Date) {
            ps.setDate(colPos, new java.sql.Date(((java.util.Date) value)
                    .getTime()));
        } else if (value instanceof java.sql.Time) {
            ps.setTime(colPos, (java.sql.Time) value);
        } else if (value instanceof Double) {
            ps.setDouble(colPos, (Double) value);
        } else if (value instanceof Float) {
            ps.setFloat(colPos, (Float) value);
        } else if (value instanceof Integer) {
            ps.setInt(colPos, (Integer) value);
        } else if (value instanceof Long) {
            ps.setLong(colPos, (Long) value);
        } else {
            ps.setObject(colPos, value);
        }
    }

    // Executable
    public int execute() throws SQLException {
        return execute((Object[]) null);
    }

    public int execute(Object... params) throws SQLException {
        int records = -1;
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

            records = statement.executeUpdate();
            wasFound = records > 0;
        } catch (NullPointerException npe) {
            wasFound = false;
            throw new SQLException("Database connection needed");
        }

        return records;
    }
    
    public boolean found() {
        return wasFound;
    }

}
