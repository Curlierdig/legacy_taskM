# ⚡ Quick Fix: MongoDB Connection Error

## Your Situation
- ✅ MongoDB 7.0 is **installed** on your system
- ❌ MongoDB is **not running**
- ❌ You're getting: `connect ECONNREFUSED 127.0.0.1:27017`

## Solution: Start MongoDB

### Option 1: Start MongoDB Service (If Available)

**Try this first:**
```powershell
# Run PowerShell as Administrator
net start MongoDB
```

**If that works**, you're done! Test with:
```powershell
mongosh
```

### Option 2: Start MongoDB Manually (Recommended for You)

Since MongoDB is installed, start it manually:

**Easy way:**
```powershell
.\start-mongodb-manual.bat
```

**Or manually:**

1. **Create data directory:**
   ```powershell
   mkdir C:\data\db
   ```

2. **Start MongoDB:**
   ```powershell
   cd "C:\Program Files\MongoDB\Server\7.0\bin"
   .\mongod.exe --dbpath C:\data\db
   ```

3. **⚠️ Keep that window open!** MongoDB needs to keep running.

4. **In a NEW terminal, test:**
   ```powershell
   mongosh
   # Should connect successfully!
   ```

5. **In another terminal, run your app:**
   ```powershell
   .\run-app.bat
   ```

## After Starting MongoDB

Once MongoDB is running:

1. **Verify connection:**
   ```powershell
   mongosh
   # Should see: "test>"
   # Type: exit
   ```

2. **Run your application:**
   ```powershell
   .\run-app.bat
   ```

3. **Open browser:** http://localhost:8080

## Make MongoDB Start Automatically (Optional)

If you want MongoDB to start automatically with Windows:

1. **Install as Windows Service:**
   - Re-run MongoDB installer
   - Choose "Repair" or "Modify"
   - Check "Install MongoDB as a Service"
   - This will make it start automatically

2. **Or create a scheduled task** to run `start-mongodb-manual.bat` on startup

## Still Having Issues?

See `FIX_MONGODB_CONNECTION.md` for more options, including MongoDB Atlas (cloud, no installation needed).
