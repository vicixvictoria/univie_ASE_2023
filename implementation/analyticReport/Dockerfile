FROM openjdk:21-ea-17-jdk-slim-buster
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} analyticReport-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/analyticReport-0.0.1-SNAPSHOT.jar"]