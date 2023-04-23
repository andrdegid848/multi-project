# Multi-project

This project consists of five labs. The first lab is a console application, and all the rest are interconnected.


## Lab1

There are several banks that provide financial services for money transactions. The bank has accounts and customers. 

The client has a first name, last name, address and passport number. There are three types of accounts: debit, deposit and credit. There are also commissions, a central bank that manages banks. There are mechanisms for withdrawing, replenishing, transferring money and canceling a transaction.

Put into practice the principles from SOLID, GRASP, GoF patterns.


## Lab2

It is necessary to design and create database tables and implement a connection to the database using JDBS, Hibernate and MyBatis. It is also necessary to implement a DAO layer for each case and write the corresponding tests.

## Lab3

It is necessary to implement a change in the database schema through migrations. 

Connect to Spring Data project. Create repositories for existing entities. It is necessary to implement a method that returns all child entities by the identifier of the parent. It is necessary to implement a method that returns parent entities by a specific value. 

Connect to Spring MVC project. Implement CRUD for each entity that can be accessed via HTTP. For all created classes - controllers, make an interface - Swagger.

## Technology stack

Java 17
  
  Gradle
  
  Spring Boot
  
  Spring Data JPA

  Spring MVC
  
  Project Lombok
  
  PostgreSQL
  
  H2DB

  Mockito
