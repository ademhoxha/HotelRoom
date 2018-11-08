FROM openjdk:8-jdk-alpine
ADD target/eureka-server.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","eureka-server.jar"]