FROM openjdk:17-alpine

EXPOSE 8081

ADD target/todo_api_service-0.0.1-SNAPSHOT.jar TodoApi.jar

ENTRYPOINT ["java", "-jar", "/TodoApi.jar"]