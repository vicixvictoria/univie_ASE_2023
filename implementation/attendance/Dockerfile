FROM openjdk:21-ea-17-jdk-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ase_project-attendance.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ase_project-attendance.jar"]