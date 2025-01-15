
# Word Frequency Analysis API

This project provides a REST API to analyze the frequency of a target word in a given text and find similar words based on specific criteria. The API is implemented using Spring Boot and supports Docker for easy deployment. SonarQube integration is included for code quality and test coverage analysis.

---

## **Features**
- Analyze the frequency of a target word in a given text.
- Identify similar words using the Levenshtein distance algorithm.
- Validates input for empty or invalid values.
- Provides test coverage and static code analysis using SonarQube and JaCoCo.

---

## **Prerequisites**
1. **Java**: JDK 17 or later.
2. **Gradle**: Installed for building the project.
3. **Docker**: Installed and running for containerization.
4. **SonarQube**: Installed locally or accessible remotely for code quality analysis.

---

## **Build and Run the Application**

### **1. Build the Project**
To build the Spring Boot project and generate the JAR file:
```bash
./gradlew clean build
```

The JAR file will be created in the `build/libs/` directory.

---

### **2. Run the Application Using Docker**

#### **2.1. Build the Docker Image**
Use the provided `Dockerfile` to build a Docker image:
```bash
docker build -t wordfrequency-api:latest .
```

#### **2.2. Run the Docker Container**
Start the application container:
```bash
docker run -p 8080:8080 --name wordfrequency-container wordfrequency-api:latest
```

#### **2.3. Access the Application**
Once the container is running, the API will be accessible at:
```
http://localhost:8080
```

---

## **Endpoints**

### **POST /api/word-frequency**
**Description**: Analyzes the frequency of a target word in the provided notebook entry and identifies similar words.

**Request Body**:
```json
{
  "noteBookEntry": "Word Words Wor word",
  "targetWord": "Word"
}
```

**Response Body**:
```json
{
  "frequency": 1,
  "similarWords": ["Words", "Wor"]
}
```

---

## **SonarQube Integration**

### **1. Start SonarQube Using Docker**
Run SonarQube locally using Docker:
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
```

Access SonarQube at:
```
http://localhost:9000
```

### **2. Generate the JaCoCo Report**
Run tests and generate the JaCoCo test coverage report:
```bash
./gradlew clean test jacocoTestReport
```

The JaCoCo XML report will be created at:
```
build/reports/jacoco/test/jacocoTestReport.xml
```

### **3. Run SonarQube Analysis**
Run the SonarQube analysis:
```bash
./gradlew sonarqube
```

Ensure your `build.gradle` is configured for SonarQube:
```groovy
sonarqube {
    properties {
        property "sonar.projectKey", "wordfrequency"
        property "sonar.host.url", "http://localhost:9000"
        property "sonar.login", "your_sonarqube_token"
        property "sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml"
    }
}
```

### **4. View SonarQube Report**
Navigate to your project dashboard in SonarQube to view:
- **Test Coverage**
- **Code Smells**
- **Bugs**
- **Vulnerabilities**

---

## **Troubleshooting**

### **Docker Issues**
- If the container doesn’t start, check the logs:
  ```bash
  docker logs wordfrequency-container
  ```

### **SonarQube Issues**
- Ensure the SonarQube server is running and accessible at `http://localhost:9000`.
- Verify the `sonar.login` token is correct in your `build.gradle`.

---

## **License**
This project is licensed under the MIT License. See the LICENSE file for details.
