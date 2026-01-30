@echo off
echo ========================================
echo Starting MongoDB
echo ========================================
echo.

REM Check if MongoDB service exists
sc query MongoDB >nul 2>&1
if %errorlevel% equ 0 (
    echo MongoDB service found. Starting...
    net start MongoDB
    if %errorlevel% equ 0 (
        echo.
        echo MongoDB started successfully!
        echo.
        echo Testing connection...
        timeout /t 2 >nul
        mongosh --eval "db.version()" 2>nul
        if %errorlevel% equ 0 (
            echo Connection successful!
        ) else (
            echo MongoDB is starting, please wait a few seconds...
        )
    ) else (
        echo.
        echo ERROR: Failed to start MongoDB service.
        echo.
        echo Possible solutions:
        echo 1. Run this script as Administrator
        echo 2. Check if MongoDB is installed
        echo 3. Install MongoDB: See MONGODB_WITHOUT_DOCKER.md
    )
) else (
    echo MongoDB service not found.
    echo.
    echo MongoDB is not installed or not installed as a service.
    echo.
    echo Options:
    echo 1. Install MongoDB: See MONGODB_WITHOUT_DOCKER.md
    echo 2. Use MongoDB Atlas (cloud, free): See MONGODB_WITHOUT_DOCKER.md
    echo 3. If MongoDB is installed but not as service, start manually:
    echo    cd "C:\Program Files\MongoDB\Server\<version>\bin"
    echo    mongod.exe --dbpath C:\data\db
    echo.
)

pause
