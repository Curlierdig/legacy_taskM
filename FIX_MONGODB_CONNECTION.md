# Fix MongoDB Connection Error

## Your Error: "connect ECONNREFUSED 127.0.0.1:27017"

This means MongoDB server is **not running**. You have `mongosh` (MongoDB shell) installed, but the MongoDB server needs to be started.

## Quick Fix Options

### Option 1: Start MongoDB Service (If Installed as Service)

**MongoDB is installed on your system!** Try starting the service:

```powershell
# Run PowerShell as Administrator, then:
net start MongoDB
```

**Or use the script:**
```powershell
.\start-mongodb.bat
```

**If service doesn't exist**, MongoDB is installed but not as a service. See Option 4 below.

**Try this first:**
```powershell
# Run as Administrator
net start MongoDB
```

**Or use the script:**
```powershell
.\start-mongodb.bat
```

**If service doesn't exist**, MongoDB is not installed. See Option 2 or 3 below.

### Option 2: Install MongoDB (Recommended)

1. **Download MongoDB:**
   - Go to: https://www.mongodb.com/try/download/community
   - Select: Windows, MSI package
   - Download

2. **Install:**
   - Run the installer
   - Choose "Complete" installation
   - **IMPORTANT:** Check "Install MongoDB as a Service"
   - This will automatically start MongoDB

3. **Verify:**
   ```powershell
   net start MongoDB
   mongosh
   ```

### Option 3: Use MongoDB Atlas (Easiest - No Installation)

**This is the fastest option if you don't want to install anything:**

1. **Sign up:** https://www.mongodb.com/cloud/atlas/register
2. **Create free cluster** (takes 2-3 minutes)
3. **Get connection string**
4. **Update `src/main/resources/application.properties`:**

   Replace:
   ```properties
   spring.data.mongodb.host=localhost
   spring.data.mongodb.port=27017
   spring.data.mongodb.database=taskmanager
   ```
   
   With:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@YOUR_CLUSTER.mongodb.net/taskmanager?retryWrites=true&w=majority
   ```

5. **That's it!** No MongoDB installation needed.

See `MONGODB_WITHOUT_DOCKER.md` for detailed Atlas setup.

### Option 4: Start MongoDB Manually (If Installed But Not as Service) ⚡ QUICKEST FOR YOU

**MongoDB is installed on your system!** Start it manually:

**Easy way - Use the script:**
```powershell
.\start-mongodb-manual.bat
```

**Or manually:**

1. **Create data folder:**
   ```powershell
   mkdir C:\data\db
   ```

2. **Start MongoDB:**
   ```powershell
   cd "C:\Program Files\MongoDB\Server\<version>\bin"
   .\mongod.exe --dbpath C:\data\db
   ```
   
   **⚠️ Keep this window open while using the application!**

3. **Test in new terminal:**
   ```powershell
   mongosh
   # Should connect successfully now!
   ```

4. **Then run your application in another terminal:**
   ```powershell
   .\run-app.bat
   ```

## Recommended: MongoDB Atlas (Option 3)

For quickest setup without installation:
- ✅ Free forever
- ✅ No installation
- ✅ Works immediately
- ✅ Cloud-hosted (accessible from anywhere)

Takes about 5 minutes to set up.

## After Fixing

Once MongoDB is running, verify:

```powershell
mongosh
# Should connect successfully
```

Then run your application:
```powershell
.\run-app.bat
```

Or:
```powershell
.\set-java-home.bat
.\mvnw.cmd spring-boot:run
```

## Still Having Issues?

See `MONGODB_TROUBLESHOOTING.md` for more detailed troubleshooting.
