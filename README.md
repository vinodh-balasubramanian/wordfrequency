
# Word Frequency Analysis API

This project provides a REST API to analyze the frequency of a target word in a given text and find similar words based on specific criteria. The API is implemented using Spring Boot and supports Docker for easy deployment. SonarQube integration is included for code quality and test coverage analysis.

---

## **Features**
- Analyze the frequency of a target word in a given text.
- Identify similar words using the Levenshtein distance algorithm.
- Validates input for empty or invalid values.
- Provides test coverage and static code analysis using SonarQube and JaCoCo.
- OpenAPI documentation

---
## **Scope of the Solution**
The goal of this project was to develop a simple REST API that performs word frequency analysis on a notebook entry 
and identifies similar words. 
The solution involves:

1. Implementing a simple Spring Boot-based API to handle HTTP requests.
2. Utilization of Apache Commons Text for finding similar words based on the Levenshtein distance.
3. Validating input to ensure no empty or invalid values are processed.
4. Extensive test coverage (up to 90%) and the resolution of code quality issues (0% unresolved issues) by integrating SonarQube for code quality checks and JaCoCo for test coverage analysis.
5. Well API documentation using OpenAPI, alongside clean code practices with meaningful comments to ensure maintainability

---
## **Time Dedicated**
The time dedicated to this solution was approximately 4-6 hours. During this time, the following tasks were completed:

- Development of core features like frequency analysis and similar word identification. 
- Validation of user input and error handling.
- Extensive unit test cases.
- Containerization of the application using Docker. 
- Integration of SonarQube for code quality and test coverage analysis.

---
## **Future Improvements (If More Time Was Available)**
If more time was available, I would have expanded the solution to include the following:

- Scalability Improvements: Use Elasticsearch for optimize the Levenshtein distance algorithm for handling larger datasets.
- Additional Features: Use Bedrock Embeddings for Semantic Search functionality
- UI Integration: Create a simple front-end application using React to allow users to interact.
- Caching: Caching the API responses, as the response will remain the same for each identical request.

---
## **OpenAPI Documentation**
This project utilizes OpenAPI 3.0 for generating API documentation. OpenAPI is a specification for defining and documenting RESTful APIs in a standardized. 
It provides a clear structure to describe the API endpoints, request parameters, response formats.
Swagger UI is provided to visualize and interact with the API directly from the browser.

Once the application is running, the Swagger UI can be accessed at:

http://localhost:8080/swagger-ui/index.html

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
