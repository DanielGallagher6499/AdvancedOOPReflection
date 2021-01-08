# Advanced OOP Reflection Application
***
You are required to create a Java application that uses reflection to analyse an arbitrary Java
Application Archive (JAR) and calculates one or more design and structural metrics for each
of the component classes in its object graph. You are free to use any number of or combination
of metrics, including LOCs, SLOC, CP, cyclomatic complexity, positional stability, CK metrics
and MOOD metrics.

- Your application should process a JAR archive provided by a user, compute one or more metrics for each class and present the results in a structured format, i.e. a JavaFX GUI or a command line console user-interface. 

- The results should be stored in an instance of MicroStream DB and have one or more stream-based queries for displaying results (a single query will suffice).

- You are also required to provide a UML diagram of your design and to JavaDoc your code.

- Do not use modules as this will complicate the reflection process with additional responsibilities for handling access rights and dealing with legacy packages.

# Objective
***
Your objective is to implement the required features and then extend, modify and refactor
your code to create an elegant application design. You should aim to accomplish the following
in your application:
- Apply the SOLID principles throughout your application.
- Group together cohesive elements into methods, types and packages.
- Loosely-couple together all programme elements to the maximum extent possible.
- Create a reusable set of abstractions that are defined by interfaces and base classes.
- Encapsulate at method, type and package level.
- Apply creational, structural and behavioural design patterns throughout the application where appropriate.
