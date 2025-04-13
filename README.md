# Auto Tests Application – Spring Boot + SQLite + Dockerized Setup

A production-ready **Spring Boot** application packaged as a **standalone JAR** (with embedded Tomcat), using **SQLite** for file-based persistence. 
This application is containerized via Docker with persistent volumes for database.

---

## Project structure & Build

### Build Tools
- **Java**: JDK 17
- **Build Tool**: Maven 3.8.5
- **Application**: Built as a **standalone JAR** with embedded Tomcat
- **Database**: Uses SQLite as embedded database (file-based)
- **Frontend**: Handled via `frontend-maven-plugin` (for Angular)

### Build & Packaging
- Maven profile: `prod`
- Output: `target/auto-tests-<version>.jar`

Build with:
```bash
mvn clean package -Pprod -DskipTests
```

---

## 🐳 Docker Setup (Production)

### 🔒 Environment Variables
Set these before starting the app (define in a `.env` file in root project path):

```bash
AUTO_TEST_SECRET_KEY=your_base64_secret_key
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

---

## 📄 Dockerfile

```dockerfile
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
```

---

## 🧩 Docker Compose (docker-compose.yml)

```yaml
services:
  app:
    image: auto-tests-prod:0.0.1    # Docker image name and version (should match the version in pom.xml for consistency)
    container_name: auto-tests-app  # Friendly name for the container
    build:
      context: .                # Use the current directory as the build context
      dockerfile: Dockerfile    # Specify the Dockerfile to use for building the image
    env_file:
      - .env                    # Load environment variables from the .env file
    ports:
      - "8080:8080"             # Map host port 8080 to container port 8080
    volumes:
      - db:/app/db              # Mount named volume "db" to persist database data at /app/db
      - uploads:/app/uploads    # Mount named volume "uploads" to persist uploaded files at /app/uploads
    environment:
      DB_PATH: /app/db/dbProd.sqlite  # Custom environment variable for the database file path inside the container
      UPLOADS_PATH: /app/uploads/     # Custom environment variable for the uploads directory inside the container
    restart: unless-stopped     # Automatically restart the container unless it is manually stopped

volumes:
  db:       # Named volume to persist SQLite database file (dbProd.sqlite)
  uploads:  # Named volume to persist uploaded resources (e.g., images, logo, licenses)
```

### 📂 Volume Mapping
| Volume Name        | Container Path  | Purpose                     |
|--------------------|-----------------|-----------------------------|
| auto-tests_db      | `/app/db`       | Persistent SQLite database  |
| auto-tests_uploads | `/app/uploads/` | Persistent upload resources |

---

## 🚀 Running the Application

### First-Time Setup (Detailed steps)

#### Step 1: Prepare Environment variables
Create an `.env` file in root project path (recommended for Docker Compose):

```env
AUTO_TEST_SECRET_KEY=your_base64_secret_key
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

#### Step 2: Build project (Optional for local testing)

```bash
mvn clean package -Pprod -DskipTests
```
> This is not required if using Docker to build automatically.

#### Step 3: Start Application with Docker Compose
```bash
docker-compose up --build -d
```

The above command will execute:
- Build the image from source code
- Create the necessary volumes (if not exist)
- Create container from built image and run in background
- Start the service on port `8080`

#### Step 4: Verify the application is running
Open your browser at:
```
http://localhost:8080
```

Use the following command to verify the container is running:
```bash
docker ps
```

#### Step 5: Initialize database structure

After checking and ensuring that the application has launched successfully and the database (dbProd.sqlite) has been created, 
run the following command to initialize the database structure for the application:

```bash
docker exec -i auto-tests-app bash /app/docker-initdb.sh
```

#### Step 6: Check logs (Optional)
```bash
docker-compose logs -f
```

---

### Redeploy with Code changes
#### 1. Update source code
#### 2. Rebuild and restart app with:

```bash
docker-compose up --build -d
```
> Persistent data in volumes (`auto-tests_db`, `auto-tests_uploads`) will not be affected.

---

### Stopping and Remove
**Stop** all services and **remove** containers:
```bash
docker-compose down
```

---

### Restarting Services
Restart without rebuilding:
```bash
docker-compose restart
```

---

### Viewing Logs
Follow logs for all services:
```bash
docker-compose logs -f
```

Or view logs for specific service:
```bash
docker-compose logs -f app
```

---

## Maintainer
Created by **Công Thoại**  
Email: [dcthoai@gmail.com](mailto:dcthoai@gmail.com)
