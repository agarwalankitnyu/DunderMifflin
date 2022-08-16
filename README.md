# Welcome to StackEdit!

Hi! I'm your first Markdown file in **StackEdit**. If you want to learn about StackEdit, you can read me. If you want to play with Markdown, you can edit me. Once you have finished with me, you can create new files by opening the **file explorer** on the left corner of the navigation bar.


# DunderMifflin

CRUD Customer API that would help power an online paper store (Dunder Mifflin Paper Company).  API is able to Create, View, List, Update and Delete customers.

## Running Instructions

Makr sure **Maven** and **Java** is installed on your system and is set to path.

Navigate to directory and run command:
 ```java
mvn spring-boot:run
```

This will bring up the service on port 8080. Make sure you have no other service using port 8080.

## Testing

Under src/test/java directory a test file **CustomerApiTests** is located. It is an end to end test file and requires that the serve is already running on port 8080.

Open the project with any IDE and run the test file. This file has all the test to cover the endpoints.

You can also test the api through command line or with **Postman**.

## Scaling

If I were to scale this service to serve millions of customer, I will make the following changes:

1. Use a Relational Database like MySQL instead of in memory database that I am currently using.
2. Use Auto Scaling to bring up multiple instances of the service based on demand and put it behind a load balancer.
3. Add some kind of authentication and authorization to the service.
4. Add a caching layer between db and service for faster reads.
5. Implement database sharing and/or setting up read replicas depending on the requirements and situation.
