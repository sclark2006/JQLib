package com.fclark.jqlib.main;

import static com.fclark.jqlib.Parameter.$PAR;
import static com.fclark.jqlib.Query.select;

import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;

import com.fclark.jqlib.Entity;
import com.fclark.jqlib.Environment;
import com.fclark.jqlib.Resultable;
import com.fclark.jqlib.column.Column;

/**
 * 
 * @author Frederick Clark<br>
 * @version 0.1 Feb 8th, 2011<br>
 * @version 0.2 Mar 1, 2011.    Las entidades generadas incluyen un constructor default, 
 *                              un constructor que recibe un String alias y un m�todo est�tico alias(String alias),
 *                              para cambiar el alias.<br>
 * @version 0.3 Mar 22, 2011.   Se ajust� para que los campos sean generados con el identificador "final" y con el tipo de 
 *                              de dato correspondiente.<br>
 *                              Se ajust� para que busque y especifique el Primary Key a cada tabla generada.<br>
 * @version 0.4 Mar 29, 2011.   Se completaron los TODO javafy, incluir sin�nimos.<br>
 * @version 0.5 Mar 30, 2011.   Se complet� el TODO typeAware. Se cre� la copia actual llamada EntityImporterMD (por MetaData)
 *                              a partir del programa original EntityImporter. <br>
 * 
 * Mar 2, 2011. Frederick Clark <br>
 * @done: Agregar la opci�n "javafy", para escoger si se quiere  que los campos se generen con nombres siguien el est�ndard de Sun
 * o con el nombre real del campo en la base de datos. @dateDone: Mar 29, 2011.   <br>
 * @done: Agregar una opci�n para escoger si se quiere generar solo las tablas, solo los sin�nimos, solo las vistas o todas.<br><br>
 * 
 * Mar 16, 2011. @dateDone: Mar 29, 2011<br>
 * @done: Agregar la opci�n "typeAware" para escoger si se quiere que el tipo de la columna corresponda al tipo de datos 
 * real de la columna en la base de datos (Ej. VARCHAR2, CHAR, DATE, NUMBER, etc.). @dateDone: Mar 30, 2011.<br><br>
 * 
 * Mar 22, 2011.<br>
 * TODO: Agregar una opci�n para elegir si quiero crear cada entidad en una clase separada o crear una clase que represente
 * un esquema especificado, con todas las entidades como clases internas.<br><br>
 * 
 * 
 * Mar 30, 2011.<br>
 * TODO: Agregar una opci�n para compilar automaticamente los archivos .java generados.<br><br>
 */
  
public class EntityImporterMD {

    private static final String GENERATED_MESSAGE = "/**\n * This class was generated by the JQLib EntityImporter \n"+
                                                    " * by %1$s on %2$s\n *\n */\n";
    private static final String IMPORTS = "import com.fclark.jqlib.Entity;\nimport com.fclark.jqlib.column.*;\n";
    
    /**
     * Permite la ejecuci�n del EntityImporter desde la l�nea de comandos.
     * 
     * @param args          Arreglo de {@link String}con los par�metros para gestionar la importaci�n de la(s) entidad(es). 
     * @throws Exception    En caso de ocurra alguna excepci�n, esta es lanzada a la interfaz del usuario.
     */
    public static void main(String[] args) throws Exception {
        
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/depot_development"; //args[0] -- connString
        String user = "root"; //args[1] -- user
        String pwd = ""; //args[2] --password
        String schema = "depot_development"; //args[3]; -- schema
        String table = "*";  //args[5]; -- tableName, use "*" for all tables
        String pkg = "com.fclark.jqlib.test.models.depot"; //args[6]; -- package where generated classes will belong
        String outPath = System.getProperty("user.dir").concat("\\src\\"); //args[7] path where the generated java files will be located    
        boolean includeSynonyms = false;
        boolean typeAware = true;
        boolean javafy = true;
        String tableExpr = "s$";
        String columnExpr = "";
        boolean asPojo = false;
        Environment.setConnection(DriverManager.getConnection(url, user,pwd));
        
        importModel(schema,table,pkg, outPath, typeAware, includeSynonyms, javafy, tableExpr, columnExpr, asPojo);
        
    }
    
    private static String javafy(String objName, boolean camelCase) {
        String[] arrVal = objName.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        int i=0;
        if(camelCase) {
            sb.append(arrVal[0]);
            i = 1;
        }

        for(; i < arrVal.length; i++)
            sb.append(arrVal[i].substring(0,1).toUpperCase() + arrVal[i].substring(1));
                        
        return sb.toString();
    }
    
    
    /**
     * Realiza la importaci�n de las entidades, de acuerdo a los par�metros especificados.  
     * 
     * 
     * @param schema                Patr�n del nombre del (los) esquema(s) de base de datos donde se encuentran las tablas a importar.<br> 
     * @param table                 Patr�n del nombre de la(s) tabla(s) a ser importadas. Si se pone "*" se importar�n todas las tablas.<br> 
     * @param pkg                   Nombre del package para el que se generar�n las clases generadas.
     * @param outPath               Ruta donde se colocar�n los archivos generados, sin incluir el package.
     * @param typeAware             Indica si el tipo de dato real de una columna determinar� el tipo de dato del campo generado en la clase, Ej: VARCHAR, DATE, NUMBER.<br>
     *                              Si es false, todos los campos generados ser�n del tipo {@link Column}
     * @param includeSynonyms       Si es true, se importar�n los sin�nimos de las tablas indicadas (si tienen) como subclases de las
     *                              clases correspondientes a dichas tablas. De lo contrario solo se importar�n las tablas indicadas.   
     * @param javafy                Si es true, las clases ser�n generadas usando la nomenclatura de Java para las clases y campos. 
     *                              De lo contrario se usar�n los nombres reales de las tablas y columnas.  
     * @param tableExpr          Solo se usa este par�metro si la opci�n "javafy" es true. Sirve para eliminar alg�n prefijo que se utilice en la nomenclatura
     *                              de las tablas y sin�nimos al momento de generar los nombres de las clases en formato Java.
     * @throws Exception            Cualquier excepci�n ocurrida durante el proceso de importaci�n es lanzada a la interfaz del usuario o al m�todo que invoque a importModel();
     * 
     */
     public static void importModel(String schema, String table, String pkg, String outPath, 
             boolean typeAware, boolean includeSynonyms, boolean javafy, String tableExpr,
             String columnExpr, boolean asPojo) throws Exception {
        StringBuilder classData; 
        ResultSet tablesRes, columnsRes, pkRes;  
        Hashtable<String, Integer> pkTable = new Hashtable<String, Integer>();
        long millis;
        int cantClases = 0;
        
        //Get all the tables with the specified criteria
        table = table.equals("*") ? null: table;
        tablesRes = Environment.getConnection().getMetaData().getTables(null, schema, table, new String[]{"TABLE"});
        
        //For all tables found
        
        while(tablesRes.next()) {
            cantClases++;
            String tableName = tablesRes.getString("TABLE_NAME");
            String className = tableName;
            millis = System.currentTimeMillis();
            //javafies the table name
            if(javafy) {
                //removes the prefixes, if any was specified
                if(!tableExpr.isEmpty() )
                    className = className.replaceFirst(tableExpr,"");
                
                className = javafy(className,false);
                System.out.print("generando clase \""+ className+ "\" de la entidad \""+tableName+"\" ...");
            }
            else
                System.out.print("generando clase \""+ tableName+ "\" ...");
            classData  = new StringBuilder("package ").append(pkg).append(";\n\n");
            classData.append(String.format(GENERATED_MESSAGE, System.getProperty("user.name"),new Date().toString()));
            classData.append(IMPORTS);
            classData.append("\npublic class ").append(className).append(" extends Entity<").append(className);
            classData.append("> {\n\n");
            //Busca el Primary Key, si existe.
            pkRes = Environment.getConnection().getMetaData().getPrimaryKeys(null, schema, tableName);            
            while(pkRes.next())
                pkTable.put(pkRes.getString("COLUMN_NAME"), pkRes.getInt("KEY_SEQ"));
            pkRes.close();
            //Columns
            //Search for table columns
            columnsRes = Environment.getConnection().getMetaData().getColumns(null, schema, tableName, null);
            //For all columns found            
            while(columnsRes.next()) {
                String colName = columnsRes.getString("COLUMN_NAME");
                int sqlType =  columnsRes.getInt("DATA_TYPE");
                String colType = "Column";
                //System.out.println(colName +", "+ sqlType);
                //Determines the column datatype; default to "Column"
                if(typeAware && Column.DATA_TYPES_MAP.containsKey(sqlType)) {
                    colType = Column.DATA_TYPES_MAP.get(sqlType); 
                }
                
                classData.append("\tpublic final ").append(colType).append(" ");
                //javafies the column name
                if(javafy) {
                    classData.append(javafy(colName.replaceFirst(columnExpr, ""),true));
                }
                else
                    classData.append(colName);
                
                classData.append(" = new ").append(colType).append("(\"").append(colName);
               //Check if the current column is part of the primary key
                Integer keySeq = pkTable.get(colName);
                if(keySeq != null)
                    classData.append("\",this,").append(keySeq).append(");\n");
                else
                    classData.append("\",this);\n");
            }//columns
            columnsRes.close();
            //Constructors
            String entityName = "\t\t this.setName(\""+ tableName + "\");\n";
            
            String cons = "\tpublic %1$s() {\n "+
                          "\t\t super();\n";
            if(javafy)
                cons += entityName;            
            cons += "\t}\n\n";            
            
            cons += "\tpublic %1$s(String alias) {\n "+
                    "\t\t super(alias);\n";
            if(javafy)
                cons += entityName;
            cons += "\t}\n\n";
            
            classData.append("\n" +String.format(cons, className));

            //Methods
            String methods = "\t public static %1$s alias(String alias) {\n";
            methods+= "\t\t return new %1$s(alias);\n";
            methods+= "\t}\n";            
            
            classData.append(String.format(methods, className));            
            
            //End class
            classData.append("}\n");
            //Crea el archivo de la clase
            writeToFile(classData.toString(), outPath + pkg.replace(".", "\\")+"\\"+ className.concat(".java"));
            System.out.println(((System.currentTimeMillis() - millis)/1000) + " sec");
            if(includeSynonyms) {
                importSynonyms(schema, tableName, className, pkg, outPath);             
            }
            pkTable.clear();
        }//tables
        System.out.println("\nProceso de generaci�n terminado. Se generaron "+ cantClases + " clases.");
        tablesRes.close();
    }
    
    static void writeToFile(String classData, String path) {
        FileWriter writer;
        //Crea el archivo de la clase
        try {
            writer = new FileWriter(path);
            writer.write(classData);
            writer.flush();
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    static void importSynonyms(String schema, String tableName, String tableClassName, String pkg, String outPath) throws Exception {
        StringBuilder synData; 
        Resultable synQry;
        ResultSet synRes;        
        long millis = 0;
        
        //Busca los sin�nimos de la tabla actual
        ALL_SYNONYMS syn = new ALL_SYNONYMS(); 
        synQry = select(syn.SYNONYM_NAME).
                from(syn).
                where(syn.OWNER.equal($PAR)).
                and(syn.TABLE_NAME.equal($PAR));
        
        
        System.out.println("\tSearching for synonyms...");
        //Search for table synonyms
        //synRes = Environment.getConnection().getMetaData().getTables(null, schema, table, new String[]{"SYNONYM"});

        synRes = synQry.asResultSet(schema, tableName);
        boolean synFound = false;
        while(synRes.next()) {
            String synName = synRes.getString("SYNONYM_NAME");
            String className = synName;
            System.out.print("\t generando clase del sin�nimo " + synName+ " ...");            
            millis = System.currentTimeMillis();
            synFound = true;            
            synData =  new StringBuilder("package "+pkg+";\n\n");
            synData.append("public class " + className);
            synData.append(" extends " + tableClassName + " {\n") ;
            //Constructor
            synData.append("\tpublic " + className + "() {\n");
            synData.append("\t\tsuper();\n\t}\n");                            
            synData.append("\tpublic " + className + "(String alias) {\n");
            synData.append("\t\tsuper(alias);\n\t}\n");
            //
            synData.append("\tpublic static " + className + " alias(String alias) {\n");
            synData.append("\t\treturn new " + className + "(alias);\n\t}\n");
            synData.append("}\n");
            writeToFile(synData.toString(), outPath + pkg.replace(".", "\\")+"\\"+ className.concat(".java"));
            System.out.println(((System.currentTimeMillis() - millis)/1000) + " sec");
        }
        synRes.close();
        if(!synFound)
            System.out.println("\tNo synonyms for table " + tableName + " were found.");
    }
        
    static class ALL_SYNONYMS extends Entity<ALL_SYNONYMS> {
        public final Column OWNER = new Column("OWNER",this),
                        TABLE_NAME = new Column("TABLE_NAME",this),
                        SYNONYM_NAME = new Column("SYNONYM_NAME",this);             
    }
    

}
