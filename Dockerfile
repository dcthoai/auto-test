# ==============================
# STAGE 1: Build with Maven
# ==============================
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy only Maven config files first to leverage Docker cache
COPY pom.xml ./

# Cache Maven dependencies
RUN mvn dependency:go-offline -B

# Install Node.js v18.20.5 and npm 10.8.2
RUN mvn frontend:install-node-and-npm

# Copy package.json and package-lock.json for caching node_modules
COPY package.json package-lock.json ./
RUN mvn frontend:npm

# Copy Angular config files
COPY angular.json server.ts tsconfig*.json ./

# Copy full source code
COPY src ./src

# Build backend and frontend (Angular)
RUN mvn clean package -Pprod -DskipTests

# ==============================
# STAGE 2: Run JAR
# ==============================
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy initial database sql files and script sh
COPY dbchangelog/init_*.sql ./initdb/
COPY docker-initdb.sh ./

# Install sqilte3
RUN apt-get update && apt-get install -y sqlite3

# Copy Spring Boot JAR from the build stage
COPY --from=build /app/target/auto-tests-*.jar app.jar

# Copy static Angular build files (already integrated by Spring Boot)
COPY --from=build /app/target/classes/static /app/static

# Expose application port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
