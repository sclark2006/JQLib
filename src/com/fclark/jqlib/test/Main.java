package com.fclark.jqlib.test;

import com.fclark.jqlib.Environment;
import com.fclark.jqlib.test.models.depot.Order;
import com.fclark.jqlib.test.models.depot.Product;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Arrays;



public class Main {
    /**
     * @param args
     */
    
    public static void main(String[] args) throws Exception {
        testMySQL();
        
        

    }
    
    static void testMySQL() throws Exception {
        initDB();
      // Inicialización de Entidades
     Order  orden = Order.alias("o");

     orden = orden.find(1);
      if(orden != null)
        System.out.println("orden = "+ Arrays.toString(orden.values()));
           
      
      for(Product prod: Product.alias("p").findAll())
          System.out.println("product = " + Arrays.toString(prod.values()));
      
      closeConn();
    }

    static void initDB() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/depot_development";

        Environment.setConnection(DriverManager.getConnection(url, "root", ""));
        Environment.getConnection().setAutoCommit(true);
    }
    
    static void closeConn() throws Exception {
     Environment.getConnection().close();   
    }
}
