FROM maven:3.9.11-eclipse-temurin-21
WORKDIR /workspace
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml .
COPY src src
RUN mvn -B -DskipTests package

WORKDIR /workspace
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/workspace/target/appointment-service-0.0.1-SNAPSHOT.jar"]
