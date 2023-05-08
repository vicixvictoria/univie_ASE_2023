FROM openjdk:21-ea-17-jdk-slim-buster
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} maintenance-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/maintenance-0.0.1-SNAPSHOT.jar"]