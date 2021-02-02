# JQLib

Java Query Library is a set of classes and interfaces that easies the creation and execution of SQL queries and DMLs, and also works as an ORM. This was my first GitHub repository, near 2011 and it started as a way to easy my work at the company I was working for at that time, since they didn't allowed almost any external libs to use and most of the code was raw JSP over Oracle Databases.


You can check the [TODO List](https://github.com/sclark2006/JQLib/wiki/ToDo) if you like.

## Some examples as DSL or Fluent Typed DML:

### Inserting
```java
//Use like a query builder.
//Create the instance of the entity class
Table1 test = new Table1();

insertInto(test).columns(test.COL1,test.COL2,test.COL3)
  .values("EXAMPLE FIVE",21,75).execute();
```
### Updating
```java
update(Table1.class)
 .set(test.COL2.to(25),
      test.COL3.to(35)
      )
  .where(test.COL1.equal("EXAMPLE NINE"))
  .execute();
```
### Deleting
```java
deleteFrom(Table1.class)
  .where(test.COL1.equal("EXAMPLE NINE"))
  .execute();
```

## It can be used as an ORM as well
```java
Table1 p = new Table1();
p.COL1.set("EXAMPLE TEN");
p.COL2.set(1);
p.COL3.set(2);
p.insert(); // also p.save();

//Retrieve all the data from a table
System.out.println("findAll()");
for(Table1 p : test.findAll()) {
     System.out.println(Arrays.toString(p.values()));
 }
 ```

Sample print out

       findAll()
       [EXAMPLE FIVE, 0, 0]
       [EXAMPLE FIVE, 21, 75]
       [EXAMPLE FINAL, 1, 2]
       [EXAMPLE TEN, 1, 2]
       [EXAMPLE EIGHT, 1, 2]
       [EXAMPLE EIGHT, 1, 2]
       [EXAMPLE NINE, 25, 35]
       [null, 0, 0]

```java
// Information that matchs a criteria
 System.out.println("\nfind(Predicate)");
 for(Table1 p : test.find(test.COL1.equal("EXAMPLE EIGHT")) ) {
     System.out.println(Arrays.toString(p.values()));
 }
 ```
Sample print out

       find(Predicate)
       [EXAMPLE EIGHT, 1, 2]
       [EXAMPLE EIGHT, 1, 2]
       
## Current Project State

Since I long left that company and never had the situation again where I couldn't use my preferred framework, I stopped working on it. But it was very interesting at that time and help me to understand how ORM works and how to get the best out of my needs to create something that could be useful to somebody else.

If you have any sugestions or inquiries about this project, drop me an email at sclark2006 at gmail dot com. If you plan to use it or think you can add any improvements, just open an issue and I'll be glad to attend.
