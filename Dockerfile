# ==============================
# STAGE 1: Build with Maven
# ==============================
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -Pprod -DskipTests

# ==============================
# STAGE 2: Run JAR
# ==============================
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy JAR file from builder
COPY --from=build /app/target/*.jar app.jar

# Create a folder for SQLite DB and application logs
RUN mkdir -p /app/db && chmod -R 777 /app/db
RUN mkdir -p /mnt/logs && chmod -R 777 /mnt/logs

# Set ENV for database path (optional)
ENV DB_PATH=/app/db/dbProd.sqlite

# Expose app port
EXPOSE 8080

# Run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
