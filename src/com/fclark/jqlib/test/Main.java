package com.fclark.jqlib.test;

import com.fclark.jqlib.Environment;
import com.fclark.jqlib.test.models.depot.LineItem;
import com.fclark.jqlib.test.models.depot.Order;
import static com.fclark.jqlib.Query.selectVar;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;


public class Main {
    /**
     * @param args
     */
    
    public static void main(String[] args) throws Exception {
        
        test();
    }
     static void test() throws Exception {
        initDB();
       
      Order orden = Order.alias("o").find(1);
      System.out.println("orden = "+ Arrays.toString(orden.values()));
      
      for(LineItem item: orden.getLineItems() )
          System.out.println("item = " + Arrays.toString(item.values()));
      
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
