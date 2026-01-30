# Maven Setup Guide

## Option 1: Install Maven (Recommended)

### Windows Installation:

1. **Download Maven:**
   - Visit: https://maven.apache.org/download.cgi
   - Download `apache-maven-3.9.x-bin.zip`

2. **Extract and Setup:**
   - Extract to `C:\Program Files\Apache\maven` (or your preferred location)
   - Add to System Environment Variables:
     - Variable: `MAVEN_HOME` = `C:\Program Files\Apache\maven`
     - Add to PATH: `%MAVEN_HOME%\bin`

3. **Verify Installation:**
   ```powershell
   mvn --version
   ```

### Linux Installation:

```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install maven

# Verify
mvn --version
```

### macOS Installation:

```bash
# Using Homebrew
brew install maven

# Verify
mvn --version
```

## Option 2: Use Maven Wrapper (No Installation Needed)

I'll create a Maven Wrapper for you so you don't need to install Maven globally.

## Option 3: Use IDE (Easiest for Development)

### IntelliJ IDEA:
1. Open the project
2. IntelliJ will automatically detect Maven
3. Right-click on `pom.xml` → Maven → Reload Project
4. Run `TaskManagerApplication.java` directly

### Eclipse:
1. File → Import → Existing Maven Projects
2. Select the project folder
3. Right-click project → Run As → Spring Boot App

### VS Code:
1. Install "Extension Pack for Java"
2. Open the project folder
3. VS Code will detect Maven automatically
4. Use the Run button in the editor

## Running the Application

### With Maven installed:

```bash
# Compile and run
mvn spring-boot:run

# Or build first, then run
mvn clean install
java -jar target/task-manager-1.0.0.jar
```

### With Maven Wrapper (after setup):

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### With IDE:
- Simply run `TaskManagerApplication.java` as a Java application

## Troubleshooting

### "mvn is not recognized":
- Maven is not installed or not in PATH
- Use Option 2 (Maven Wrapper) or Option 3 (IDE)

### Java version error:
- Make sure Java 17+ is installed
- Check: `java -version`
- Update JAVA_HOME if needed

### Port 8080 already in use:
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```
