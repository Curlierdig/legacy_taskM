# Quick Start Guide

## Prerequisites Check

✅ **Java 21** is installed (detected)

## Step 1: Setup MongoDB (Choose One Option)

### Option A: Docker (Easiest - Recommended)

1. Install Docker Desktop: https://www.docker.com/products/docker-desktop
2. Run MongoDB:
   ```powershell
   docker-compose up -d
   ```
3. Verify it's running:
   ```powershell
   docker ps
   ```

### Option B: Install MongoDB Locally

See `MONGODB_SETUP.md` for detailed instructions.

**Quick Windows Install:**
1. Download: https://www.mongodb.com/try/download/community
2. Run installer (choose "Complete" installation)
3. MongoDB will start automatically as a Windows service

### Option C: MongoDB Atlas (Cloud - Free)

1. Sign up at: https://www.mongodb.com/cloud/atlas
2. Create free cluster
3. Get connection string
4. Update `application.properties` with your connection string

## Step 2: Run the Application

### Option A: Using Maven Wrapper (No Maven Installation Needed)

```powershell
# Windows
.\mvnw.cmd spring-boot:run

# If you get permission error, try:
.\mvnw.cmd.cmd spring-boot:run
```

### Option B: Using IDE (Easiest)

**IntelliJ IDEA / Eclipse / VS Code:**
1. Open the project folder
2. Open `src/main/java/com/taskmanager/TaskManagerApplication.java`
3. Click "Run" button (▶️) or press `Shift+F10`

The IDE will automatically:
- Download Maven dependencies
- Compile the project
- Start the application

### Option C: Install Maven (If you prefer command line)

See `MAVEN_SETUP.md` for installation instructions.

Then run:
```powershell
mvn spring-boot:run
```

## Step 3: Access the Application

Open your browser and go to:
```
http://localhost:8080
```

## Step 4: Build TailwindCSS (Optional)

The CSS is already built, but if you make changes:

```powershell
# Install dependencies (one time)
npm install

# Build CSS
.\build-css.bat
```

Or use TailwindCSS CDN (see templates for commented CDN link).

## Troubleshooting

### "mvnw.cmd not found"
- The Maven wrapper files need to be created
- Use Option B (IDE) instead, or install Maven

### "Cannot connect to MongoDB"
- Make sure MongoDB is running
- Check: `docker ps` (if using Docker)
- Or: `Get-Service MongoDB` (if installed locally)
- Verify port 27017 is not blocked by firewall

### "Port 8080 already in use"
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

### Application starts but shows errors
- Check MongoDB connection
- Verify MongoDB is running on localhost:27017
- Check application logs in console

## Default Login

The application creates sample data on first run:
- **Username:** admin
- **Password:** admin

## Next Steps

- See `README.md` for full documentation
- See `MONGODB_SETUP.md` for MongoDB details
- See `MAVEN_SETUP.md` for Maven installation
