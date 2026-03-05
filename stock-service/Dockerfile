# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 as builder
WORKDIR /app
COPY base-domains ./base-domains
RUN cd base-domains && mvn clean install -DskipTests
COPY stock-service/pom.xml ./stock-service/
COPY stock-service/src ./stock-service/src
RUN cd stock-service && mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/stock-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
