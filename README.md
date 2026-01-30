# Task Manager - Modern Spring Boot Application

A modern task management application built with Spring Boot, MongoDB, and TailwindCSS.

## Tech Stack

- **Backend**: Java 17+, Spring Boot 3.2.0
- **Database**: MongoDB
- **Frontend**: Thymeleaf templates with TailwindCSS
- **Build Tool**: Maven (with Maven Wrapper included)

## Features

- ✅ Task Management (CRUD operations)
- ✅ Project Management
- ✅ Task Search and Filtering
- ✅ Task Statistics Dashboard
- ✅ Modern UI with TailwindCSS
- ✅ Responsive Design

## Quick Start

**See `QUICK_START.md` for the fastest way to get running!**

### Prerequisites

- ✅ Java 17 or higher (Java 21 detected)
- MongoDB (see setup options below)
- Node.js (optional, for TailwindCSS)

### Option 1: Docker (Easiest)

1. **Start MongoDB:**
   ```powershell
   docker-compose up -d
   ```

2. **Run the application:**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
   Or use the startup script:
   ```powershell
   .\start-app.bat
   ```

3. **Open browser:** http://localhost:8080

### Option 2: IDE (Recommended for Development)

1. Open project in IntelliJ IDEA / Eclipse / VS Code
2. Open `src/main/java/com/taskmanager/TaskManagerApplication.java`
3. Click Run (▶️)
4. Open browser: http://localhost:8080

### Option 3: Install MongoDB Locally

See `MONGODB_SETUP.md` for detailed instructions.

## Setup Instructions

### 1. MongoDB Setup

Choose one option:

- **Docker** (easiest): `docker-compose up -d`
- **Local Install**: See `MONGODB_SETUP.md`
- **MongoDB Atlas** (cloud): See `MONGODB_SETUP.md`

### 2. Run the Application

**With Maven Wrapper (no Maven installation needed):**
```powershell
.\mvnw.cmd spring-boot:run
```

**With IDE:**
- Simply run `TaskManagerApplication.java`

**With installed Maven:**
```powershell
mvn spring-boot:run
```

### 3. Build TailwindCSS (Optional)

The CSS is already built. If you make changes:

```powershell
npm install
.\build-css.bat
```

Or use TailwindCSS CDN (uncomment CDN link in templates).

## Default Login

The application creates sample data on first run:
- **Username:** admin
- **Password:** admin

## Project Structure

```
src/
├── main/
│   ├── java/com/taskmanager/
│   │   ├── controller/     # Spring MVC Controllers
│   │   ├── model/          # MongoDB Entity Models
│   │   ├── repository/     # MongoDB Repositories
│   │   ├── service/        # Business Logic Services
│   │   ├── config/         # Configuration (Data Initializer)
│   │   └── TaskManagerApplication.java
│   └── resources/
│       ├── static/
│       │   ├── css/        # TailwindCSS files
│       │   └── js/         # JavaScript files
│       ├── templates/      # Thymeleaf templates
│       └── application.properties
└── test/
```

## API Endpoints

### Tasks
- `GET /` - Task list and management page
- `POST /agregar` - Create new task
- `POST /actualizar` - Update existing task
- `POST /eliminar` - Delete task
- `GET /editar/{id}` - Edit task page

### Projects
- `GET /proyectos` - Project list and management page
- `POST /proyectos/agregar` - Create new project
- `POST /proyectos/eliminar/{id}` - Delete project

### Search
- `GET /busqueda` - Search tasks with filters

## Configuration

Edit `src/main/resources/application.properties` to configure:
- MongoDB connection settings
- Server port
- Thymeleaf settings

## Documentation

- **`QUICK_START.md`** - Fastest way to get running
- **`MONGODB_SETUP.md`** - MongoDB installation and setup
- **`MAVEN_SETUP.md`** - Maven installation (if needed)
- **`SETUP.md`** - TailwindCSS setup

## Troubleshooting

### Maven not found
- Use Maven Wrapper: `.\mvnw.cmd spring-boot:run`
- Or use an IDE (recommended)

### MongoDB connection error
- Make sure MongoDB is running
- Check: `docker ps` (if using Docker)
- Verify port 27017 is accessible

### Port 8080 already in use
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

## License

This project is open source and available for use.
