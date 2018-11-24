# HotelRoom
The application manages the Hotel Rooms in a Microservices Design. It is possible to open or close an Hotel Room refered to a customer reservation, and it will possible for the customer to execute some operations in his room.
All the application is designed to be **reactive**.

## Spring 5 Modules
- Spring Cloud Netflix Eureka (Client and Server)
- Spring Cloud Netflix Zuul
- Spring WebFlux
- Spring ReactiveMongoRepository
- Spring Coud Config Server

## Application Architecture
Microservices Architecture: 
All Microservices can be deployed in a Docker Container or executed without container.
Zuul proxy is the only microservices that users will see.
The Aggregate-Gateway microservice in used for the transaction operations, or for more elaborated operations. 
Each simple Microservices (like Hotel) points to a MLab DB.
The configuration is centralized so is possible to refresh configuration without restart the application.

![applicationarchitecture 1 1](https://user-images.githubusercontent.com/18213427/48966299-27373b80-efcf-11e8-86b9-041766322234.jpg)
