@echo off
echo ========================================
echo Starting MongoDB Manually
echo ========================================
echo.

REM Try to find MongoDB installation (MongoDB 7.0 detected)
set MONGODB_PATH=
if exist "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe" (
    set MONGODB_PATH=C:\Program Files\MongoDB\Server\7.0\bin
    goto :found
)
if exist "C:\Program Files\MongoDB\Server" (
    for /d %%d in ("C:\Program Files\MongoDB\Server\*") do (
        if exist "%%d\bin\mongod.exe" (
            set MONGODB_PATH=%%d\bin
            goto :found
        )
    )
)

:found
if "%MONGODB_PATH%"=="" (
    echo ERROR: MongoDB not found in default location.
    echo.
    echo Please start MongoDB manually:
    echo 1. Find your MongoDB installation
    echo 2. Navigate to: <MongoDB>\Server\<version>\bin
    echo 3. Run: mongod.exe --dbpath C:\data\db
    echo.
    echo Or install MongoDB: See MONGODB_WITHOUT_DOCKER.md
    pause
    exit /b 1
)

echo Found MongoDB at: %MONGODB_PATH%
echo.

REM Create data directory if it doesn't exist
if not exist "C:\data\db" (
    echo Creating data directory...
    mkdir C:\data\db
)

echo Starting MongoDB...
echo.
echo IMPORTANT: Keep this window open while using the application!
echo.
echo To stop MongoDB, press Ctrl+C in this window.
echo.

cd /d "%MONGODB_PATH%"
mongod.exe --dbpath C:\data\db

pause
