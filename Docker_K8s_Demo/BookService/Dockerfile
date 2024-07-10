FROM openjdk:17-alpine

EXPOSE 8080

ADD target/Spring_mysql_k8s-0.0.1-SNAPSHOT.jar BookService.jar

ENTRYPOINT ["java", "-jar", "/BookService.jar"]