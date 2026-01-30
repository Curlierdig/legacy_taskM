# Fix JAVA_HOME Issue

## Quick Fix (Temporary - Current Session Only)

Run this script first:
```powershell
.\set-java-home.bat
```

Then run the application:
```powershell
.\mvnw.cmd spring-boot:run
```

Or use the all-in-one script:
```powershell
.\run-app.bat
```

## Permanent Fix (Set JAVA_HOME Permanently)

### Method 1: Using PowerShell (Run as Administrator)

1. **Find your Java installation:**
   ```powershell
   java -XshowSettings:properties -version 2>&1 | Select-String "java.home"
   ```
   
   This will show something like: `java.home = C:\Program Files\Java\jdk-21`

2. **Set JAVA_HOME permanently:**
   ```powershell
   # Run PowerShell as Administrator, then:
   [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-21", "Machine")
   ```
   
   Replace `C:\Program Files\Java\jdk-21` with your actual Java path (remove `\bin` if present).

3. **Add to PATH:**
   ```powershell
   $currentPath = [System.Environment]::GetEnvironmentVariable("Path", "Machine")
   [System.Environment]::SetEnvironmentVariable("Path", "$currentPath;$env:JAVA_HOME\bin", "Machine")
   ```

4. **Restart PowerShell** and verify:
   ```powershell
   echo $env:JAVA_HOME
   java -version
   ```

### Method 2: Using System Properties (GUI)

1. **Right-click "This PC" → Properties**
2. **Click "Advanced system settings"**
3. **Click "Environment Variables"**
4. **Under "System variables", click "New":**
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-21` (your Java path)
5. **Edit "Path" variable:**
   - Click "Edit"
   - Click "New"
   - Add: `%JAVA_HOME%\bin`
   - Click OK on all dialogs
6. **Restart PowerShell/Command Prompt**

### Method 3: Using setx (Command Prompt as Administrator)

```cmd
REM Find Java path first
java -XshowSettings:properties -version 2>&1 | findstr "java.home"

REM Then set (replace with your actual path)
setx JAVA_HOME "C:\Program Files\Java\jdk-21" /M
setx PATH "%PATH%;%JAVA_HOME%\bin" /M
```

**Note:** `/M` flag requires Administrator privileges.

## Verify JAVA_HOME is Set

```powershell
echo $env:JAVA_HOME
java -version
mvnw.cmd --version
```

## Common Java Installation Paths

- `C:\Program Files\Java\jdk-21`
- `C:\Program Files\Java\jdk-17`
- `C:\Program Files (x86)\Java\jdk-21`
- `C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot`

## If Java is Not Found

If `java -version` doesn't work, Java might not be in PATH. Find it manually:

1. Search for `java.exe` in File Explorer
2. Common locations:
   - `C:\Program Files\Java\jdk-21\bin\java.exe`
   - `C:\Program Files\Common Files\Oracle\Java\javapath\java.exe`

3. Use the parent directory (without `\bin`) as JAVA_HOME

## Quick Test

After setting JAVA_HOME, test with:
```powershell
.\mvnw.cmd --version
```

This should show Maven version without errors.
