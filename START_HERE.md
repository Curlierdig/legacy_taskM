# 🚀 START HERE - Task Manager Setup

## ✅ What You Have

- ✅ **Java 21** installed (compatible with this project)
- ✅ **Maven Wrapper** included (no Maven installation needed!)
- ✅ **Complete Spring Boot application** ready to run
- ✅ **Docker Compose** file for easy MongoDB setup

## 🎯 Quickest Way to Run (3 Steps)

### Step 1: Fix JAVA_HOME (If Needed)

If you get "JAVA_HOME not found" error:

**Quick Fix (Temporary):**
```powershell
.\set-java-home.bat
.\mvnw.cmd spring-boot:run
```

**Or use all-in-one script:**
```powershell
.\run-app.bat
```

**Permanent Fix:** See `FIX_JAVA_HOME.md`

### Step 2: Start MongoDB

**⚠️ IMPORTANT:** If you get "ECONNREFUSED" error, MongoDB is not running. See `FIX_MONGODB_CONNECTION.md`

**Option A: MongoDB Atlas (Cloud - Free - No Installation) ⭐ FASTEST**
- Sign up: https://www.mongodb.com/cloud/atlas/register
- Create free cluster (2-3 minutes)
- Update `application.properties` with connection string
- See `MONGODB_WITHOUT_DOCKER.md` Option 2 for details
- **No installation needed!**

**Option B: Install MongoDB Locally**
- Download: https://www.mongodb.com/try/download/community
- Install and start MongoDB service
- See `MONGODB_WITHOUT_DOCKER.md` Option 1 for detailed instructions

**Option C: Docker (If you have Docker installed)**
```powershell
docker-compose up -d
```

### Step 3: Run the Application

**Option A: Using Maven Wrapper**
```powershell
.\mvnw.cmd spring-boot:run
```

**Option B: Using IDE (Easiest - Recommended)**
1. Open project in IntelliJ IDEA / Eclipse / VS Code
2. Open `src/main/java/com/taskmanager/TaskManagerApplication.java`
3. Click Run (▶️)
   - IDE will automatically set JAVA_HOME and run the app

**Option C: Using All-in-One Script**
```powershell
.\run-app.bat
```

### Step 3: Open Browser

Go to: **http://localhost:8080**

## 📋 What Happens on First Run

1. Maven Wrapper downloads Maven automatically (first time only)
2. Application downloads Spring Boot dependencies (first time only)
3. Application connects to MongoDB
4. Sample data is created automatically:
   - Admin user (username: `admin`, password: `admin`)
   - Sample projects
   - Sample tasks

## 🐛 Troubleshooting

### "JAVA_HOME not found" or "JAVA_HOME not set"
- **Quick Fix:** Run `.\set-java-home.bat` first, then `.\mvnw.cmd spring-boot:run`
- **Or use:** `.\run-app.bat` (does both automatically)
- **Permanent Fix:** See `FIX_JAVA_HOME.md`
- **Easiest:** Use an IDE (IntelliJ IDEA, Eclipse, VS Code) - it handles JAVA_HOME automatically

### "mvnw.cmd not found" or "mvnw.cmd cannot be executed"
- **Solution:** Use an IDE instead (IntelliJ IDEA, Eclipse, or VS Code)
- Or install Maven: See `MAVEN_SETUP.md`

### "connect ECONNREFUSED 127.0.0.1:27017" or "Cannot connect to MongoDB"
- **MongoDB is not running!**
- **Quick Fix:** See `FIX_MONGODB_CONNECTION.md`
- **Fastest Solution:** Use MongoDB Atlas (cloud, free, no installation)
- **Or:** Install MongoDB locally: See `MONGODB_WITHOUT_DOCKER.md`
- **Or:** Start MongoDB service: `net start MongoDB` (as Administrator)

### "docker-compose not recognized" or "docker not found"
- **Solution:** Use MongoDB Atlas (cloud, free) - no Docker needed
- Or install MongoDB locally: See `MONGODB_WITHOUT_DOCKER.md`
- **Check if MongoDB is running:**
  ```powershell
  docker ps
  # Should show mongodb container
  ```
- **Or if installed locally:**
  ```powershell
  Get-Service MongoDB
  ```
- **Start MongoDB:**
  ```powershell
  docker-compose up -d
  # OR
  net start MongoDB
  ```

### "Port 8080 already in use"
- Change port in `src/main/resources/application.properties`:
  ```properties
  server.port=8081
  ```

### Application starts but shows errors
- Check MongoDB is running on `localhost:27017`
- Check application console for error messages
- Verify MongoDB connection in `application.properties`

## 📚 More Help

- **`FIX_JAVA_HOME.md`** - Fix JAVA_HOME issues (permanent setup)
- **`MONGODB_WITHOUT_DOCKER.md`** - MongoDB setup without Docker
- **`QUICK_START.md`** - Detailed quick start guide
- **`MONGODB_SETUP.md`** - MongoDB installation options (with Docker)
- **`MAVEN_SETUP.md`** - Maven installation (if needed)
- **`README.md`** - Full project documentation

## 🎨 UI Styling

The TailwindCSS is already built. If you need to rebuild:

```powershell
npm install
.\build-css.bat
```

Or use TailwindCSS CDN (uncomment CDN link in HTML templates).

## ✨ You're Ready!

Just follow the 3 steps above and you'll have the application running!
