Java Query Library is a set of classes and interfaces that easies the creation and execution of SQL queries and DMLs, and also works as an ORM.
Project main commiter:
Frederick Clark /sclark2006
You can folow the TODOList in here: https://github.com/sclark2006/JQLib/wiki/ToDo
Here some examples:
//============================================================================

       //Como Query Builder.
       //Crea la instancia de la clase Entity
        PRUEBA prueba = new PRUEBA();        

      //Insert statement
         insertInto(prueba).columns(prueba.EJEMPLO,prueba.COLUMNA1, prueba.COLIMNA2)
         .values("EJEMPLO CINCO",21,75).execute();

      //Update statement
  update(PRUEBA.class)
        .set(prueba.COLUMNA1.to(25),
             prueba.COLIMNA2.to(35)
            )
         .where(prueba.EJEMPLO.equal("EJEMPLO NUEVE"))
         .execute();

      //Delete statement
  deleteFrom(PRUEBA.class)
         .where(prueba.EJEMPLO.equal("EJEMPLO NUEVE"))
         .execute();

      //Como ORM 
       PRUEBA p = new PRUEBA();        
        p.EJEMPLO.set("EJEMPLO DIEZ");
        p.COLUMNA1.set(1);
        p.COLIMNA2.set(2);
        p.insert(); // también p.save();

// Todos los datos de una tabla
                  System.out.println("findAll()");
        for(PRUEBA p : prueba.findAll()) {
            System.out.println(Arrays.toString(p.values()));
        }
//Impresión de resultado
/*
findAll()
[EJEMPLO CINCO, 0, 0]
[EJEMPLO CINCO, 21, 75]
[EJEMPLO FINAL, 1, 2]
[EJEMPLO DIEZ, 1, 2]
[EJEMPLO OCHO, 1, 2]
[EJEMPLO OCHO, 1, 2]
[EJEMPLO NUEVE, 25, 35]
[null, 0, 0]
*/

// Todos los datos de una tabla que cumplan con un criterio

        System.out.println("\nfind(Predicate)");
        for(PRUEBA p : prueba.find(prueba.EJEMPLO.equal("EJEMPLO OCHO")) ) {
            System.out.println(Arrays.toString(p.values()));
        }
/*
find(Predicate)
[EJEMPLO OCHO, 1, 2]
[EJEMPLO OCHO, 1, 2]
*/