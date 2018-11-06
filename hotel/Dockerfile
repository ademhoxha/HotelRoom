FROM openjdk:8-jdk-alpine
ADD target/hotel.jar hotel.jar
EXPOSE 9002
ENTRYPOINT ["java","-jar","hotel.jar"]