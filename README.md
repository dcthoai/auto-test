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

## 🐳 Docker Setup (Testing production with Docker local)

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

## 🚀 Running the Application (Docker local)

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

Or if you want to run the two steps of building the image and running the container separately:

1. **Run build**:
   ```bash
   docker-compose build
   ```

2. **Run up** (after building):
   ```bash
   docker-compose up -d
   ```

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

## Deploy to Production Server with Docker Registry (Docker Hub)
Use the configuration guidelines from the previous steps and follow the instructions below to deploy the application to a production server instead of running it with Docker local.

### Case 1. If you skipped the setup step for testing production on Docker local
If you **haven't run** `docker-compose up --build -d` as part of testing production with Docker local, follow these steps to build the image and push it to Docker Hub:

#### Step 1. **Build Docker image**:
```bash
docker build -t <your-username>/<repo-name>:<tag> .
```

Example:
```bash
docker build -t thoaidc/auto-tests-app:0.0.1 .
```

#### Step 2. **Login to Docker Hub (for first-time use)**:
```bash
docker login
```

#### Step 3. **Push the image to Docker Hub**:
```bash
docker push <your-username>/<repo-name>:<tag>
```

Example:
```bash
docker push thoaidc/auto-tests-app:0.0.1
```

---

### Case 2. If you have already set up testing production with Docker local as described above

If you **have already built** using:
```bash
docker-compose up --build -d
```

The Docker image built will not have the standard tag like `<your-username>/<repo-name>:<tag>`. You will need to **re-tag** the image to push it to Docker Hub.

#### Step 1. **View the list of built images**:
```bash
docker images
```

#### Step 2. **Re-tag the image**:
```bash
docker tag <local-image>:<tag> <your-username>/<repo-name>:<tag>
```

Example:
```bash
docker tag auto-tests-prod:0.0.1 thoaidc/auto-tests-app:0.0.1
```

#### Step 3. **Push to Docker Hub**:
```bash
docker push <your-username>/<repo-name>:<tag>
```

Example:
```bash
docker push thoaidc/auto-tests-app:0.0.1
```

---

### Notes

- The default build from `docker-compose` can be re-tagged multiple times to serve different environments such as dev, prod, latest, etc.
- When deploying on the production server, you should pull the image from Docker Hub instead of building it from source.

### Running the Application on the Production Server

After the Docker image has been pushed to Docker Hub, follow these steps to launch the application (directly on the server):

#### Step 1: Log in to Docker Hub
```bash
docker login
```

#### Step 2: Pull the Docker Image from Docker Hub

Pull the Docker image that was pushed to Docker Hub to the production server:

```bash
docker pull thoaidc/auto-tests-app:0.0.1
```

#### Step 3: Configure `docker-compose.prod.yml` and `.env` (only first-time setup or update if there are changes for future times)
On the server, create a configuration file for Docker Compose in the directory where you want to install the application:

**docker-compose.prod.yml**
```yaml
services:
  app:
    image: thoaidc/auto-tests-prod:0.0.1  # Pull image from Docker Hub
    container_name: auto-tests-app        # Friendly name for the container
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
  db:      # Named volume to persist SQLite database file (dbProd.sqlite)
  uploads: # Named volume to persist uploaded resources (e.g., images, logo, licenses)
```

**.env**
```env
AUTO_TEST_SECRET_KEY=your_base64_secret_key
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
DB_PATH=/app/db/dbProd.sqlite
UPLOADS_PATH=/app/uploads/
```

#### Step 4: Run Docker Compose on the Production Server

After configuring `docker-compose.prod.yml` and `.env`, cd to the application installation directory where the configuration files were created above and run Docker compose:

```bash
docker-compose -f docker-compose.prod.yml up -d
```

The command above will:
- Pull the Docker image from Docker Hub (if not already pulled).
- Initialize the container from the downloaded Docker image.
- Open port 8080 so the application can be accessed via `http://your-server-ip:8080`.

#### Step 5: Check Logs (Optional)

After the application is running, you can check the logs to ensure everything is working as expected:

```bash
docker-compose logs -f
```

Or check the logs for the specific container `app`:

```bash
docker-compose logs -f app
```

### **Redeploy with New Docker image (New application version)**

#### Step 1: **Pull the New Docker Image from Docker Hub**

Update the image tag in the `docker-compose.prod.yml` file and update environment variables in the `.env` file if there are any changes.
Then, run the following command to pull the new image from Docker Hub:

```bash
docker pull <your-username>/<repo-name>:<new-tag>
```

Example:
```bash
docker pull thoaidc/auto-tests-app:0.0.2
```

#### Step 2: **Restart Docker Compose**

After pulling the new Docker image, restart the container with the new image:

1. **Stop and remove the running containers**:

   ```bash
   docker-compose -f docker-compose.prod.yml down
   ```

2. **Restart the container with the new image**:

   After stopping and removing the old container, use the `up` command with the `-d` flag to run the containers with the new image in detached mode:

   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```

---

## Maintainer
Created by **Công Thoại**  
Email: [dcthoai@gmail.com](mailto:dcthoai@gmail.com)
