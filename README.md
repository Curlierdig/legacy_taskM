# Task Manager

A modern task and project management web application built with **Spring Boot 3.2**, **MongoDB Atlas**, **Thymeleaf**, and **Bootstrap 5.3.3**. Features include authentication with Spring Security, user roles, change history, notifications, per-task comments, CSV export, and a reports dashboard.

---

## Features

| Module | Functionality |
|---|---|
| **Tasks** | Full CRUD, statuses (Pending / In Progress / Completed), priorities, user assignment, due dates, estimated hours |
| **Projects** | Create and delete projects; group tasks by project |
| **Search** | Advanced filtering by title, status, priority, and project |
| **Reports** | Dashboard with team statistics and metrics |
| **History** | Automatic change log for tasks |
| **Notifications** | Per-user notification system |
| **Comments** | Per-task comment threads |
| **CSV Export** | Download tasks as `.csv` |
| **Authentication** | Login/register with Spring Security and BCrypt; `ADMIN` and `USER` roles |

---

## Prerequisites

| Tool | Minimum Version | Notes |
|---|---|---|
| **Java JDK** | 17+ | JDK 21 recommended |
| **Maven** | — | Included as Maven Wrapper (`mvnw` / `mvnw.cmd`) |

> MongoDB Atlas is pre-configured in `application.properties`. No local database installation is required.

---

## Quick Start

### 1. Clone the repository

```bash
git clone <REPO_URL>
cd task-manager
```

### 2. Run the application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The application will be available at: **http://localhost:8080**

That's it. The project connects to MongoDB Atlas automatically.

---

## Default Login

Sample users are created automatically on first run:

| Username | Password | Role |
|---|---|---|
| `admin` | `admin` | ADMIN |
| `juan` | `password` | USER |
| `maria` | `password` | USER |

> **Warning:** Change these passwords in production. Users are only created if the database is empty.

---

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── config/          # SecurityConfig, DataInitializer
│   │   │   ├── controller/      # MVC Controllers (Thymeleaf)
│   │   │   ├── controller/rest/ # REST API Controllers
│   │   │   ├── exception/       # GlobalExceptionHandler
│   │   │   ├── model/           # MongoDB Entities (Task, Project, User...)
│   │   │   ├── repository/      # Spring Data MongoDB Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   └── TaskManagerApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/         # Custom stylesheets (Bootstrap)
│   │       │   └── js/          # Frontend JavaScript
│   │       ├── templates/       # Thymeleaf templates (.html)
│   │       └── application.properties
├── pom.xml                      # Maven dependencies
└── railwayl.toml                # Railway deployment config
```

---

## Main Endpoints

### Views (Thymeleaf)

| Method | Route | Description |
|---|---|---|
| `GET` | `/` | Task list and management |
| `GET` | `/editar/{id}` | Task edit form |
| `POST` | `/agregar` | Create task |
| `POST` | `/actualizar` | Update task |
| `POST` | `/eliminar` | Delete task |
| `GET` | `/proyectos` | Project management |
| `GET` | `/busqueda` | Advanced search |
| `GET` | `/reportes` | Reports dashboard |
| `GET` | `/historial` | Change history |
| `GET` | `/notificaciones` | Notifications |
| `GET` | `/comentarios/{taskId}` | Task comments |
| `GET` | `/login` | Login page |
| `GET` | `/register` | Registration page |

### REST API

| Method | Route | Description |
|---|---|---|
| `GET/POST` | `/api/tasks/**` | Task CRUD (JSON) |
| `GET/POST` | `/api/projects/**` | Project CRUD (JSON) |
| `GET` | `/api/users/**` | User queries (JSON) |

---

## Available Commands

```bash
# Start the application
.\mvnw.cmd spring-boot:run          # Windows
./mvnw spring-boot:run              # Linux/macOS

# Build JAR for production
.\mvnw.cmd clean package -DskipTests

# Run the built JAR
java -jar target/task-manager-1.0.0.jar
```

---

## Deployment (Railway)

The project includes a Railway configuration (`railwayl.toml`):

1. Push the repository to GitHub.
2. Connect the repo to Railway.
3. Railway will build and deploy automatically.

---

## License

This project is open source and available for use.
