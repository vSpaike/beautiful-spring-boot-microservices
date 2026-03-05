# Building & Running Locally - Development Guide

Guide for building and running the Food Delivery microservices on your local machine without Docker.

---

## Prerequisites

### Required Tools
1. **Java 17+**
   ```bash
   java -version
   # Output: openjdk version "17.x.x"
   ```

2. **Maven 3.6+**
   ```bash
   mvn -version
   # Output: Apache Maven 3.x.x
   ```

### Installation

#### Windows
1. **Download Java 17 JDK**
   - Visit: https://www.oracle.com/java/technologies/downloads/#java17
   - Download and install

2. **Download Maven**
   - Visit: https://maven.apache.org/download.cgi
   - Extract and add to PATH

#### macOS
```bash
# Using Homebrew
brew install java@17
brew install maven
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven
```

---

## Building the Services

### Order Service

#### 1. Navigate to Order Service
```bash
cd order-service
```

#### 2. Build with Maven
```bash
mvn clean install
```

This will:
- Download dependencies
- Compile source code
- Run tests
- Create JAR file in `target/`

#### 3. Run the Service
```bash
mvn spring-boot:run
```

Or directly run the JAR:
```bash
java -jar target/order-service-0.0.1-SNAPSHOT.jar
```

**Expected Output**:
```
...
o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port(s): 8081
...
order-service started successfully
```

### Restaurant Service

In a **new terminal window**, repeat the process:

#### 1. Navigate to Restaurant Service
```bash
cd stock-service
```

#### 2. Build with Maven
```bash
mvn clean install
```

#### 3. Run the Service
```bash
mvn spring-boot:run
```

**Expected Output**:
```
...
o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port(s): 8082
...
restaurant-service started successfully
```

---

## Verifying Services are Running

### Check Port 8081 (Order Service)
```bash
# Windows (PowerShell)
Test-NetConnection -ComputerName localhost -Port 8081

# macOS/Linux
lsof -i :8081
```

### Check Port 8082 (Restaurant Service)
```bash
# Windows (PowerShell)
Test-NetConnection -ComputerName localhost -Port 8082

# macOS/Linux
lsof -i :8082
```

### Test with cURL
```bash
# Order Service
curl http://localhost:8081/api/orders

# Restaurant Service
curl http://localhost:8082/api/restaurants
```

---

## Running Tests

### Test Order Service
```bash
cd order-service
mvn test
```

### Test Restaurant Service
```bash
cd stock-service
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=OrderServiceApplicationTests#testCreateOrder
```

### Generate Test Report
```bash
mvn test surefire-report:report
open target/site/surefire-report.html  # macOS
# or on Windows: start target\site\surefire-report.html
```

---

## Clean Commands

### Clean Order Service
```bash
cd order-service
mvn clean
# Removes: target/, dependencies
```

### Clean Restaurant Service
```bash
cd stock-service
mvn clean
# Removes: target/, dependencies
```

### Full Clean (Both Services)
```bash
cd order-service && mvn clean
cd ../stock-service && mvn clean
```

---

## Troubleshooting

### Issue: "Java not found"
**Solution**: Java not installed or not in PATH
```bash
# Install Java 17
# Then add to PATH

# Verify
java -version
```

### Issue: "Maven not found"
**Solution**: Maven not installed or not in PATH
```bash
# Install Maven
# Then add to PATH (add M2_HOME/bin)

# Verify
mvn -version
```

### Issue: "Port 8081 already in use"
**Solution**: Kill the process using that port

**Windows (PowerShell)**:
```powershell
Get-NetTCPConnection -LocalPort 8081
# Note the ProcessId

Stop-Process -Id <ProcessId> -Force
```

**macOS/Linux**:
```bash
lsof -i :8081
kill -9 <PID>
```

### Issue: "Tests fail"
**Solution**: Run with verbose output
```bash
mvn test -X  # Debug mode
mvn test -e  # Show errors
```

### Issue: "Build fails - missing dependencies"
**Solution**: Check internet connection and try again
```bash
mvn clean install -U
# -U forces update of dependencies
```

### Issue: "Out of memory during build"
**Solution**: Increase Maven heap
```bash
export MAVEN_OPTS=-Xmx1024m
mvn clean install
```

---

## IDE Setup

### IntelliJ IDEA
1. Open project root folder
2. Select `pom.xml` as project file
3. Mark `src/main/java` as Sources Root
4. Mark `src/test/java` as Test Sources Root
5. Run → Run... → Select service main class

### VS Code
1. Install Extension Pack for Java
2. Open workspace
3. Press `F5` to run or debug
4. Select Java Application

### Eclipse
1. File → Import → Existing Maven Projects
2. Select project root
3. Right-click project → Run As → Maven build
4. Goal: `spring-boot:run`

---

## Development Workflow

### Full Development Cycle

#### 1. Make Code Changes
```bash
# Edit files in src/main/java
# Edit tests in src/test/java
```

#### 2. Rebuild
```bash
mvn clean install
```

#### 3. Run Locally
```bash
mvn spring-boot:run
```

#### 4. Test
```bash
curl http://localhost:8081/api/orders
curl http://localhost:8082/api/restaurants
```

#### 5. Verify Tests Still Pass
```bash
mvn test
```

---

## Maven Common Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Remove build files |
| `mvn compile` | Compile source code |
| `mvn test` | Run tests |
| `mvn package` | Build JAR file |
| `mvn install` | Build and install locally |
| `mvn clean install` | Clean + compile + test + package |
| `mvn spring-boot:run` | Run application directly |
| `mvn dependency:tree` | Show dependency tree |
| `mvn help:describe` | Get help on Maven goals |

---

## Creating Executable JAR

### Build JAR Files
```bash
# Order Service
cd order-service
mvn clean package -DskipTests
# Creates: target/order-service-0.0.1-SNAPSHOT.jar

# Restaurant Service
cd stock-service
mvn clean package -DskipTests
# Creates: target/stock-service-0.0.1-SNAPSHOT.jar
```

### Run JAR Files
```bash
# Order Service
java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar

# Restaurant Service (in another terminal)
java -jar stock-service/target/stock-service-0.0.1-SNAPSHOT.jar
```

---

## Debug Mode

### Enable Remote Debugging
```bash
# Set Maven debug
export MAVEN_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Add to spring-boot:run
mvn -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005" spring-boot:run
```

### IDE Debugging
1. Set breakpoints in code
2. Run in debug mode (Shift+F9 in most IDEs)
3. Debug console will show variable values

---

## Continuous Development with Watch Mode

### Auto-rebuild on File Change
Install `spring-boot-devtools`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

Then run:
```bash
mvn spring-boot:run
# Will auto-restart when files change
```

---

## Performance Tips

### Skip Tests During Development
```bash
mvn clean install -DskipTests
mvn spring-boot:run -DskipTests
```

### Use Offline Mode (with cached dependencies)
```bash
mvn -o clean install
```

### Parallel Builds
```bash
mvn -T 1C clean install
# -T 1C = 1 thread per CPU core
```

---

## Project Cleanup

### Remove All Generated Files
```bash
# Both services
cd order-service && mvn clean
cd ../stock-service && mvn clean

# Remove IDE files
rm -rf .idea
rm -rf *.iml
rm -rf .vscode
```

---

## Next Steps

1. **Add Database**: Implement with JPA/Hibernate
2. **Add Kafka**: Implement asynchronous messaging
3. **Add Security**: JWT authentication
4. **Add Logging**: SLF4J and Logback
5. **Add Metrics**: Micrometer integration
6. **Add API Docs**: Springdoc-OpenAPI

---

Happy coding! 🎉
