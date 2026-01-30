# MongoDB Setup Without Docker

Since Docker is not installed, here are alternative ways to set up MongoDB:

## Option 1: Install MongoDB Locally (Recommended)

### Windows Installation Steps:

1. **Download MongoDB Community Server:**
   - Visit: https://www.mongodb.com/try/download/community
   - Select:
     - Version: Latest (7.0 or 6.0)
     - Platform: Windows
     - Package: MSI
   - Click Download

2. **Run the Installer:**
   - Run the downloaded `.msi` file
   - Choose "Complete" installation
   - **Important:** Check "Install MongoDB as a Service"
   - Check "Install MongoDB Compass" (GUI tool - optional but helpful)
   - Click Install

3. **Verify Installation:**
   ```powershell
   # Check if MongoDB service is running
   Get-Service MongoDB
   
   # If not running, start it:
   net start MongoDB
   ```

4. **Test Connection:**
   ```powershell
   # Open MongoDB Shell
   mongosh
   
   # You should see: "Current Mongosh Log ID: ..."
   # Type: exit
   ```

5. **That's it!** The application will automatically connect to MongoDB on `localhost:27017`

## Option 2: MongoDB Atlas (Cloud - Free - No Installation) ⭐ RECOMMENDED FOR QUICK SETUP

This is the easiest option if you don't want to install anything. **Takes only 5 minutes!**

1. **Sign up for free account:**
   - Go to: https://www.mongodb.com/cloud/atlas/register
   - Create a free account

2. **Create a Free Cluster:**
   - Click "Build a Database"
   - Choose "FREE" (M0) tier
   - Select a cloud provider and region (closest to you)
   - Click "Create"

3. **Create Database User:**
   - Go to "Database Access"
   - Click "Add New Database User"
   - Choose "Password" authentication
   - Username: `taskmanager` (or any username)
   - Password: Create a strong password (save it!)
   - Database User Privileges: "Atlas admin" or "Read and write to any database"
   - Click "Add User"

4. **Whitelist Your IP:**
   - Go to "Network Access"
   - Click "Add IP Address"
   - Click "Allow Access from Anywhere" (for development)
   - Or add your current IP address
   - Click "Confirm"

5. **Get Connection String:**
   - Go to "Database" → "Connect"
   - Choose "Connect your application"
   - Copy the connection string (looks like: `mongodb+srv://username:password@cluster.mongodb.net/`)

6. **Update application.properties:**
   
   Open `src/main/resources/application.properties` and replace:
   ```properties
   spring.data.mongodb.host=localhost
   spring.data.mongodb.port=27017
   spring.data.mongodb.database=taskmanager
   ```
   
   With:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@YOUR_CLUSTER.mongodb.net/taskmanager?retryWrites=true&w=majority
   ```
   
   Replace:
   - `YOUR_USERNAME` with your database username
   - `YOUR_PASSWORD` with your database password
   - `YOUR_CLUSTER` with your cluster name

7. **That's it!** No MongoDB installation needed!

## Option 3: Portable MongoDB (No Installation)

1. **Download MongoDB Portable:**
   - Visit: https://www.mongodb.com/try/download/community
   - Select: ZIP package (not MSI)
   - Extract to: `C:\mongodb` (or any folder)

2. **Create Data Directory:**
   ```powershell
   mkdir C:\data\db
   ```

3. **Start MongoDB Manually:**
   ```powershell
   cd C:\mongodb\bin
   .\mongod.exe --dbpath C:\data\db
   ```
   
   Keep this window open while using the application.

4. **In a new terminal, test:**
   ```powershell
   cd C:\mongodb\bin
   .\mongosh.exe
   ```

## Verify MongoDB is Working

After setup, verify MongoDB is accessible:

```powershell
# Test connection (if MongoDB is installed)
mongosh

# Or test port
Test-NetConnection -ComputerName localhost -Port 27017
```

## Troubleshooting

### "MongoDB service not found"
- MongoDB wasn't installed as a service
- Start it manually: `C:\Program Files\MongoDB\Server\<version>\bin\mongod.exe --dbpath C:\data\db`

### "Port 27017 already in use"
- Another MongoDB instance is running
- Stop it or use a different port

### "Cannot connect to MongoDB"
- Make sure MongoDB is running
- Check firewall settings
- Verify connection string (if using Atlas)

## Recommended: Option 1 (Local Install)

For development, installing MongoDB locally is recommended:
- Fast and reliable
- No internet required
- Full control
- Free forever

The installation takes about 5 minutes and MongoDB will start automatically with Windows.
