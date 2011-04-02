package com.fclark.jqlib.test;

import static com.fclark.jqlib.DML.insertInto;
import static com.fclark.jqlib.DML.update;
import static com.fclark.jqlib.Expression.NULL;
import static com.fclark.jqlib.JQLibHelper.func;
import static com.fclark.jqlib.Parameter.$;
import static com.fclark.jqlib.Query.select;
import static com.fclark.jqlib.Query.selectVar;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDatabaseMetaData;

import com.fclark.jqlib.DML;
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.Environment;
import com.fclark.jqlib.Executable;
import com.fclark.jqlib.JQLibHelper;
import com.fclark.jqlib.Resultable;
import com.fclark.jqlib.column.Column;
import com.fclark.jqlib.oracle.OracleFunctions;
import com.unipago.modelos.PRUEBA;
import com.unipago.modelos.SUIR_C_CIUDADANO;


public class Main {
    static SUIR_C_CIUDADANO ciudadano, c;
    /**
     * @param args
     */
    
    public static void main(String[] args) throws Exception {
        String texto = "HOLA_MUNDO".toLowerCase();
        String[] arrVal = texto.split("_");
        StringBuilder sb = new StringBuilder(arrVal[0]);
        for(int i=1; i < arrVal.length; i++)            
            sb.append(arrVal[i].substring(0,1).toUpperCase() + arrVal[i].substring(1));
        
               
        System.out.println(sb.toString());
        
        System.out.println( String.format("hola %1$s, soy del %1$s", "mundo"));
        System.out.println(System.getProperty("user.name"));
        //test1();
        
        

    }
    
    static void test1() throws Exception {
        initDB();
        ResultSet rs = Environment.getConnection().getMetaData().getPrimaryKeys(null, "FCLARK", "SUIR_C_CIUDADANO");
        //ResultSet rs = Environment.getConnection().getMetaData().getTables(null, "FCLARK", null, new String[]{"SYNONYM"});
        //ResultSet rs = Environment.getConnection().getMetaData().getColumns(null, "FCLARK", "SUIR_C_CIUDADANO", null);
        ResultSetMetaData md = rs.getMetaData();
        
        for(int i=0; i < md.getColumnCount(); i++)
            System.out.print(md.getColumnName(i+1)+", ");
        System.out.println();
        while(rs.next()) {
            for(int i=0; i < md.getColumnCount(); i++)
                System.out.print(rs.getString(i+1)+", ");
            System.out.println();
        }
        closeConn();
    }
    static void test() throws Exception {
        initDB();
        String val = selectVar(OracleFunctions.SYS_CONTEXT("USERENV", "DB_NAME")); 
      System.out.println(val);
      // Inicialización de Entidades
      c = ciudadano = new SUIR_C_CIUDADANO();
      
      SUIR_C_CIUDADANO fc =  ciudadano.find(13557793); //findFirstBy(c.C_NUM_CEDULA, "00112717657");
      
      //System.out.println("values = "+ fc.toString());
      System.out.println("values = "+ Arrays.toString(fc.values()));
      
      
      Iterable<SUIR_C_CIUDADANO> clarks = ciudadano.findBy(c.C_APEPAT_CIU, "CLARK");
      
      for(SUIR_C_CIUDADANO clark: clarks)
          System.out.println("clark = " + Arrays.toString(clark.values()));
      
      closeConn();
    }

    static void initDB() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@//host/instancia";

        Environment.setConnection(DriverManager.getConnection(url, "usuario", "clave"));
        Environment.getConnection().setAutoCommit(true);
    }
    
    static void closeConn() throws Exception {
     Environment.getConnection().close();   
    }
}
