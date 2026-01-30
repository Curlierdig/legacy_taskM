# MongoDB Setup Guide

## Option 1: Install MongoDB Locally (Recommended for Development)

### Windows Installation:

1. **Download MongoDB Community Server:**
   - Visit: https://www.mongodb.com/try/download/community
   - Select Windows x64
   - Download the MSI installer

2. **Install MongoDB:**
   - Run the installer
   - Choose "Complete" installation
   - Install as a Windows Service (recommended)
   - Install MongoDB Compass (GUI tool) - optional but helpful

3. **Verify Installation:**
   ```powershell
   # Check if MongoDB service is running
   Get-Service MongoDB
   
   # Or start MongoDB manually
   net start MongoDB
   ```

4. **Test Connection:**
   ```powershell
   # Connect to MongoDB shell
   mongosh
   # Or if using older version:
   mongo
   ```

### Linux Installation:

```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install -y mongodb

# Start MongoDB
sudo systemctl start mongodb
sudo systemctl enable mongodb

# Verify
sudo systemctl status mongodb
```

### macOS Installation:

```bash
# Using Homebrew
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

## Option 2: Use Docker (Easiest)

### Prerequisites:
- Install Docker Desktop: https://www.docker.com/products/docker-desktop

### Run MongoDB with Docker:

```bash
docker run -d -p 27017:27017 --name mongodb -e MONGO_INITDB_DATABASE=taskmanager mongo:latest
```

Or use the provided `docker-compose.yml` file:

```bash
docker-compose up -d
```

## Option 3: MongoDB Atlas (Cloud - Free Tier)

1. Go to: https://www.mongodb.com/cloud/atlas
2. Create a free account
3. Create a free cluster
4. Get your connection string
5. Update `application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/taskmanager
```

## Verify MongoDB is Running

### Check if MongoDB is accessible:

```powershell
# Windows PowerShell
Test-NetConnection -ComputerName localhost -Port 27017

# Or using MongoDB shell
mongosh
# Should connect successfully
```

### Create Initial Database (Optional):

The application will create the database automatically, but you can create it manually:

```javascript
// In MongoDB shell (mongosh)
use taskmanager
db.createCollection("tasks")
db.createCollection("projects")
db.createCollection("users")
```

## Troubleshooting

### MongoDB won't start:

1. **Check if port 27017 is in use:**
   ```powershell
   netstat -ano | findstr :27017
   ```

2. **Check MongoDB logs:**
   - Windows: `C:\Program Files\MongoDB\Server\<version>\log\mongod.log`
   - Linux: `/var/log/mongodb/mongod.log`

3. **Restart MongoDB service:**
   ```powershell
   # Windows
   net stop MongoDB
   net start MongoDB
   ```

### Connection refused error:

- Make sure MongoDB is running
- Check firewall settings
- Verify the port (default: 27017)
- Check `application.properties` configuration

## Default Configuration

The application expects MongoDB at:
- **Host:** localhost
- **Port:** 27017
- **Database:** taskmanager

This is configured in `src/main/resources/application.properties`
