FROM openjdk:17-alpine

COPY  target/Product_command_service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT [ "java","-jar", "Product_command_service-0.0.1-SNAPSHOT.jar" ]

EXPOSE 9091