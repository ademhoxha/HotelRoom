FROM openjdk:8-jdk-alpine
ADD target/hotel-room-gateway-aggregate.jar hotel-room-gateway-aggregate.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","hotel-room-gateway-aggregate.jar"]