FROM openjdk:17-alpine

EXPOSE 8080

ADD target/BookApp-0.0.1-SNAPSHOT.jar BookApp.jar

ENTRYPOINT ["java", "-jar", "/BookApp.jar"]