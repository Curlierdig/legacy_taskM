@echo off
echo ========================================
echo Task Manager - Application Startup
echo ========================================
echo.

echo [1/3] Checking Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)
echo Java found!
echo.

echo [2/3] Starting MongoDB (if using Docker)...
docker ps | findstr mongodb >nul
if %errorlevel% equ 0 (
    echo MongoDB container is running
) else (
    echo MongoDB container not found. Starting with Docker Compose...
    docker-compose up -d
    timeout /t 3 >nul
)
echo.

echo [3/3] Starting Spring Boot Application...
echo This may take a minute on first run (downloading dependencies)...
echo.
.\mvnw.cmd spring-boot:run

pause
