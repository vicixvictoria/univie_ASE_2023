FROM maven:3.8.5-openjdk-17-slim AS build
COPY common/src /home/common/src
COPY common/pom.xml /home/common
RUN mvn -f /home/common/pom.xml install -Dmaven.test.skip=true
COPY maintenance/src /home/app/src
COPY maintenance/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:21-ea-17-jdk-slim-buster
COPY --from=build /home/app/target/maintenance-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "/maintenance-0.0.1-SNAPSHOT.jar"]
