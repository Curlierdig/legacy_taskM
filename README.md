# Task Manager

A modern task and project management web application built with **Spring Boot 3.2**, **MongoDB**, **Thymeleaf**, and **Bootstrap 5.3.3**. Features include authentication with Spring Security, user roles, change history, notifications, per-task comments, CSV export, and a reports dashboard.

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
| **MongoDB** | 6.0+ | Via Docker Compose, local install, or MongoDB Atlas (cloud) |
| **Docker** *(optional)* | 20+ | To run MongoDB with `docker-compose` |

---

## Local Setup

### 1. Clone the repository

```bash
git clone <REPO_URL>
cd task-manager
```

### 2. Configure environment variables

Copy the template and fill in your values:

```bash
cp .env.template .env
```

Edit `.env` with your MongoDB connection string:

```env
MONGODB_URI=mongodb+srv://<user>:<password>@cluster.mongodb.net/<database>
SERVER_PORT=8080
```

> **Important:** The `.env` file contains credentials and **must never be committed to Git**. It is already listed in `.gitignore`.

### 3. Start MongoDB

**Option A — Docker Compose (recommended):**

```bash
docker-compose up -d
```

**Option B — MongoDB Atlas (cloud):**

Set your Atlas URI in the `.env` file. No local installation required.

**Option C — Local installation:**

Start the MongoDB service according to your operating system.

### 4. Run the application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The application will be available at: **http://localhost:8080**

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
│   │   │   ├── model/           # MongoDB Entities (Task, Project, User…)
│   │   │   ├── repository/      # Spring Data MongoDB Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   └── TaskManagerApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/         # Custom stylesheets
│   │       │   └── js/          # Frontend JavaScript
│   │       ├── templates/       # Thymeleaf templates (.html)
│   │       └── application.properties
├── docker-compose.yml           # MongoDB via Docker
├── pom.xml                      # Maven dependencies
├── .env.template                # Environment variable template
└── .env                         # Environment variables (not versioned)
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

## Available Scripts

```bash
# Start the application (development)
.\mvnw.cmd spring-boot:run          # Windows
./mvnw spring-boot:run              # Linux/macOS

# Build for production
.\mvnw.cmd clean package -DskipTests

# Start MongoDB with Docker
docker-compose up -d

# Stop MongoDB
docker-compose down
```

---

## Deployment

The project includes a **Railway** configuration (`railwayl.toml`). To deploy:

1. Set the `MONGODB_URI` environment variable in the Railway dashboard.
2. Railway will automatically build and deploy using the configured commands.

---

## License

This project is open source and available for use.
