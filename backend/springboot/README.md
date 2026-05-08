# Spring Boot Backend

A Java Spring Boot REST API.

## Prerequisites

- **Java** 21 (JDK 21)
- **Gradle** (comes with wrapper)
- **IDE**: VS Code with Extension Pack for Java or IntelliJ/ Eclipse
- **Database**: PostgreSQL

## Quick Start (CLI - VS Code Integrated Terminal)

```bash
cd springboot

# Create environment file
copy .env.example .env
# Edit .env with your database credentials

# Run the application
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

## Available Gradle Commands

| Command | Description |
|---------|-------------|
| `./gradlew bootRun` | Start development server |
| `./gradlew build` | Build the project |
| `./gradlew test` | Run tests |
| `./gradlew bootJar` | Create executable JAR |

## Installing Dependencies

Edit `build.gradle` and add the dependency, then run:

```groovy
// Add to dependencies block
implementation 'org.springframework.boot:spring-boot-starter-web'
```

```bash
./gradlew build
```

## Environment Variables

Create an `application.properties` or `application.yml` in `src/main/resources/`:

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
jwt.secret=your-secret-key
```

## Project Structure

```
springboot/
├── src/main/java/tn/maiko26/
│   ├── MultiBackendApplication.java  # Entry point
│   ├── config/                       # Security, CORS
│   ├── controller/                   # REST endpoints
│   ├── service/                      # Business logic
│   ├── model/                        # Entities
│   └── repository/                   # JPA repositories
├── build.gradle
└── gradlew
```

## VS Code Extensions Recommended

- Extension Pack for Java
- Spring Boot Extension Pack