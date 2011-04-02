package com.fclark.jqlib;

/**
 * 
 * @author Frederick Clark
 * @version 0.1 11 Mar 2011. Creación de clase.
 *  Esta clase sirve como fundamento para construir queries.
 *  @version 0.2 14 Mar, 2011. Renombrada a SQLBuilder   
 *  @version 0.3 17 Mar, 2011. Adición de métodos insert()
 */
public class SQLBuilder implements Appendable, CharSequence {
    private StringBuilder builder;
    
    public SQLBuilder() {
        builder = new StringBuilder();
    }
    
    public SQLBuilder(String initial) {
        builder = new StringBuilder(initial);
    }
    
    public SQLBuilder append(CharSequence value)  {
        builder.append(value);
        return this;
    }

    public SQLBuilder append(char value)  {
        builder.append(value);
        return this;
    }

    public SQLBuilder append(CharSequence value, int start, int end) {
        builder.append(value, start,end);
        return this;
    }

    public char charAt(int index) {
        return builder.charAt(index);
    }

    public int length() {
        return builder.length();
    }

    public CharSequence subSequence(int start, int end) {
        return builder.subSequence(start, end);
    }
    
    public SQLBuilder insert(int offset, char c) {
        builder.insert(offset, c);
        return this;
    }

    public SQLBuilder insert(int offset, CharSequence s) {
        builder.insert(offset, s);
        return this;
    }
    
    public SQLBuilder insert(int offset, String str) {
        builder.insert(offset, str);
        return this;
    }
    
    @Override
    public String toString() {
        return this.builder.toString();
    }
    
    public String toSQL() {
        return this.builder.toString();
    }
    
    public Queryable<?> createQuery() throws SQLClauseException {
        String query = this.builder.toString().toLowerCase();
        //Valida el tamaño y los componentes del query.
        if(!(query.startsWith("select ") && query.contains("from ") && query.length() > 12) &&
           !query.startsWith("insert ") && !query.startsWith("update ")  && !query.startsWith("delete ")
           )
            throw new SQLClauseException(" Invalid or incomplete SQL statement : \n\"" + query + "\"");
        return new Query(this);
    }
     
}
