@echo off
echo ========================================
echo Task Manager - Application Startup
echo ========================================
echo.

REM Set JAVA_HOME if not set
if "%JAVA_HOME%"=="" (
    echo Setting JAVA_HOME...
    call set-java-home.bat
    if %errorlevel% neq 0 (
        echo Failed to set JAVA_HOME
        pause
        exit /b 1
    )
)

echo.
echo [1/2] Checking Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not working properly
    pause
    exit /b 1
)
echo Java OK!
echo.

echo [2/2] Starting Spring Boot Application...
echo This may take a minute on first run (downloading dependencies)...
echo.
.\mvnw.cmd spring-boot:run

pause
