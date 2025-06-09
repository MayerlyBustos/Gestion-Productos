FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY target/product-api-1.1.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]