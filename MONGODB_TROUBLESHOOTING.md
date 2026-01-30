# MongoDB Connection Troubleshooting

## Error: "connect ECONNREFUSED 127.0.0.1:27017"

This means MongoDB is **not running**. Here's how to fix it:

## Quick Fix

### Step 1: Check if MongoDB Service Exists

```powershell
Get-Service MongoDB
```

**If service exists:**
```powershell
# Start MongoDB service
net start MongoDB

# Or use the script:
.\start-mongodb.bat
```

**If service doesn't exist:**
MongoDB is not installed. See options below.

### Step 2: Verify MongoDB is Running

```powershell
# Test connection
mongosh

# Or check if port is open
Test-NetConnection -ComputerName localhost -Port 27017
```

## Solutions

### Solution 1: Start MongoDB Service (If Installed)

**Windows Service:**
```powershell
# Check service status
Get-Service MongoDB

# Start the service
net start MongoDB

# Or use the provided script
.\start-mongodb.bat
```

**If you get "Access Denied":**
- Run PowerShell as Administrator
- Then run: `net start MongoDB`

### Solution 2: Install MongoDB (If Not Installed)

1. **Download MongoDB:**
   - Visit: https://www.mongodb.com/try/download/community
   - Select: Windows, MSI package
   - Download and install

2. **During Installation:**
   - Choose "Complete" installation
   - **IMPORTANT:** Check "Install MongoDB as a Service"
   - This will start MongoDB automatically

3. **Verify Installation:**
   ```powershell
   Get-Service MongoDB
   net start MongoDB
   mongosh
   ```

See `MONGODB_WITHOUT_DOCKER.md` for detailed installation steps.

### Solution 3: Start MongoDB Manually (If Installed But Not as Service)

If MongoDB is installed but not running as a service:

1. **Find MongoDB installation:**
   ```powershell
   # Common locations:
   dir "C:\Program Files\MongoDB\Server"
   ```

2. **Create data directory:**
   ```powershell
   mkdir C:\data\db
   ```

3. **Start MongoDB manually:**
   ```powershell
   cd "C:\Program Files\MongoDB\Server\<version>\bin"
   .\mongod.exe --dbpath C:\data\db
   ```
   
   **Keep this window open** while using the application.

4. **In a new terminal, test:**
   ```powershell
   mongosh
   ```

### Solution 4: Use MongoDB Atlas (Cloud - No Installation)

If you don't want to install MongoDB:

1. **Sign up:** https://www.mongodb.com/cloud/atlas/register
2. **Create free cluster**
3. **Get connection string**
4. **Update `application.properties`:**
   ```properties
   spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/taskmanager
   ```

See `MONGODB_WITHOUT_DOCKER.md` Option 2 for detailed steps.

## Common Issues

### "MongoDB service not found"
- MongoDB is not installed
- Install MongoDB: See `MONGODB_WITHOUT_DOCKER.md`

### "Access Denied" when starting service
- Run PowerShell/Command Prompt as Administrator
- Right-click → "Run as Administrator"

### "Port 27017 already in use"
- Another MongoDB instance is running
- Find and stop it:
  ```powershell
  Get-Process | Where-Object {$_.ProcessName -like "*mongo*"}
  Stop-Process -Name mongod
  ```

### "Cannot create directory C:\data\db"
- Create it manually:
  ```powershell
  mkdir C:\data\db
  ```
- Or use a different path:
  ```powershell
  mongod.exe --dbpath "C:\mongodb-data"
  ```

## Verify MongoDB is Working

After starting MongoDB, verify:

```powershell
# Test connection
mongosh

# Should see:
# Current Mongosh Log ID: ...
# Connecting to: mongodb://127.0.0.1:27017/...
# test> 

# Type: exit
```

## Quick Commands Reference

```powershell
# Check if MongoDB service exists
Get-Service MongoDB

# Start MongoDB service
net start MongoDB

# Stop MongoDB service
net stop MongoDB

# Check MongoDB status
Get-Service MongoDB | Select-Object Status

# Test connection
mongosh

# Check if port is open
Test-NetConnection -ComputerName localhost -Port 27017
```

## Next Steps

Once MongoDB is running:
1. Verify connection: `mongosh`
2. Run the application: `.\run-app.bat` or `.\mvnw.cmd spring-boot:run`
3. Open browser: http://localhost:8080
