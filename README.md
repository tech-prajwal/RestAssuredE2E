# RestAssuredE2E 🚀

> **End-to-End REST API Test Automation Framework** built with Java and REST Assured, with full CI/CD integration via Jenkins and Docker support.

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Run Tests Locally (Without Docker)](#run-tests-locally-without-docker)
  - [Run Tests with Docker](#run-tests-with-docker)
- [CI/CD Integration](#cicd-integration)
  - [Jenkins Pipeline with Docker](#jenkins-pipeline-with-docker)
  - [Jenkins Pipeline without Docker](#jenkins-pipeline-without-docker)
  - [Jenkins Sample Pipeline](#jenkins-sample-pipeline)
- [Configuration](#configuration)
- [Writing Tests](#writing-tests)
- [Test Reports](#test-reports)
- [Contributing](#contributing)
- [License](#license)

---

## 📖 About the Project

**RestAssuredE2E** is a robust, production-ready API test automation framework designed for End-to-End (E2E) testing of RESTful services. It leverages the power of [REST Assured](https://rest-assured.io/) — a Java DSL for easy testing of REST services — and integrates seamlessly into CI/CD pipelines via Jenkins, with optional Docker containerization for environment-agnostic test execution.

### Key Highlights

- ✅ End-to-End REST API test coverage using REST Assured's fluent Java DSL
- 🐳 Dockerized test execution for consistent, portable environments
- ⚙️ Multiple Jenkins pipeline strategies (with Docker, without Docker, and sample template)
- 📦 Maven-based build and dependency management
- 📊 Structured test reporting

---

## 🛠 Tech Stack

| Technology        | Version / Role                                      |
|-------------------|-----------------------------------------------------|
| **Java**          | Primary programming language (98.9% of codebase)   |
| **REST Assured**  | Java DSL for REST API testing                       |
| **Maven**         | Build tool & dependency management (`pom.xml`)      |
| **JUnit / TestNG**| Test runner framework                               |
| **Docker**        | Containerization for portable test execution        |
| **Jenkins**       | CI/CD pipeline automation                           |
| **Jackson**       | JSON serialization / deserialization                |
| **Hamcrest**      | Assertion library (used internally by REST Assured) |

---

## 📁 Project Structure

```
RestAssuredE2E/
├── src/
│   ├── main/
│   │   └── java/               # Utility classes, models, helpers
│   └── test/
│       └── java/               # Test classes and test suites
│           ├── tests/          # E2E API test cases
│           ├── utils/          # Reusable request/response helpers
│           └── models/         # POJO / data model classes
├── .gitignore                  # Git ignore rules
├── Dockerfile                  # Docker image definition for test execution
├── Jenkinsfile                 # Jenkins pipeline: Docker-based execution
├── Jenkinsfile_sample          # Jenkins pipeline: template / starter
├── Jenkinsfile_withoutdocker   # Jenkins pipeline: non-Docker execution
└── pom.xml                     # Maven build configuration & dependencies
```

---

## ✅ Prerequisites

Ensure the following tools are installed before setting up the project:

| Tool        | Minimum Version | Notes                                    |
|-------------|-----------------|------------------------------------------|
| **Java JDK**| 8 or higher     | JAVA_HOME must be set                    |
| **Maven**   | 3.6+            | `mvn` must be on your PATH               |
| **Docker**  | 20.x+           | Required only for Docker-based execution |
| **Jenkins** | 2.x+            | Required only for CI/CD pipeline usage   |
| **Git**     | Any             | For cloning the repository               |

---

## 🚀 Getting Started

### Clone the Repository

```bash
git clone https://github.com/tech-prajwal/RestAssuredE2E.git
cd RestAssuredE2E
```

---

### Run Tests Locally (Without Docker)

Use Maven to compile and run all tests:

```bash
# Run all tests
mvn clean test

# Run a specific test class
mvn clean test -Dtest=YourTestClassName

# Run tests and skip compilation errors
mvn test -DskipTests=false
```

---

### Run Tests with Docker

#### Step 1 — Build the Docker Image

```bash
docker build -t restassured-e2e .
```

#### Step 2 — Run Tests Inside a Container

```bash
docker run --rm restassured-e2e
```

This spins up an isolated container, runs the full test suite, and exits automatically, keeping your local machine clean.

#### (Optional) Mount Test Reports to Host

```bash
docker run --rm -v $(pwd)/target:/app/target restassured-e2e
```

Test reports will be available in the `target/` directory on your local machine.

---

## ⚙️ CI/CD Integration

This project includes **three Jenkins pipeline variants** to cover different environments and use cases.

### Jenkins Pipeline with Docker

**File:** `Jenkinsfile`

The primary pipeline that uses Docker to build a clean test environment, execute tests inside the container, and publish results. This is the **recommended approach** for teams that use Docker agents in Jenkins.

```groovy
// Typical pipeline stages:
// 1. Checkout
// 2. Build Docker Image
// 3. Run Tests Inside Container
// 4. Publish Test Reports
// 5. Cleanup
```

**Setup in Jenkins:**
1. Create a new **Pipeline** job.
2. Set the **SCM** to your Git repository URL.
3. Set the **Script Path** to `Jenkinsfile`.
4. Ensure the Jenkins agent has Docker installed and the Jenkins user has Docker permissions.

---

### Jenkins Pipeline without Docker

**File:** `Jenkinsfile_withoutdocker`

Designed for environments where Docker is not available on the Jenkins agent. Tests run directly on the agent using the installed Java and Maven runtime.

**Setup in Jenkins:**
1. Create a new **Pipeline** job.
2. Set the **SCM** to your Git repository.
3. Set the **Script Path** to `Jenkinsfile_withoutdocker`.
4. Ensure Java and Maven are configured in **Global Tool Configuration** in Jenkins.

---

### Jenkins Sample Pipeline

**File:** `Jenkinsfile_sample`

A boilerplate / starter template for bootstrapping a new Jenkins pipeline. Use this as a reference when customizing the pipeline for different environments or extending stages (e.g., Slack notifications, artifact archiving, environment-specific configs).

---

### Triggering the Pipeline

You can configure Jenkins to trigger tests automatically on:

| Trigger           | How to configure                              |
|-------------------|-----------------------------------------------|
| **Git push**      | Webhooks → Jenkins build trigger              |
| **Pull Request**  | GitHub/Bitbucket plugin                       |
| **Scheduled run** | Cron expression in Jenkins `triggers` block   |
| **Manual**        | Click "Build Now" in Jenkins UI               |

---

## 🔧 Configuration

Test configuration (base URLs, credentials, environment settings) is typically managed via:

- **`pom.xml`** — Maven profiles, system properties, and plugin configurations.
- **Environment Variables** — Injected at runtime via Jenkins or Docker `-e` flags.
- **Properties / Config files** — Located in `src/test/resources/` (e.g., `config.properties`).

Example of passing environment-specific base URL at runtime:

```bash
mvn clean test -Dbase.url=https://api.yourenvironment.com
```

---

## ✍️ Writing Tests

Tests follow REST Assured's fluent BDD-style `given().when().then()` syntax:

```java
import io.restassured.RestAssured;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SampleApiTest {

    @Test
    public void testGetUsers() {
        RestAssured.baseURI = "https://reqres.in/api";

        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/users?page=2")
        .then()
            .statusCode(200)
            .body("page", equalTo(2))
            .body("data", not(empty()));
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"John\", \"job\": \"Developer\" }";

        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("John"))
            .body("id", notNullValue());
    }
}
```

---

## 📊 Test Reports

After running tests, Maven generates reports in the `target/` directory:

| Report Type              | Location                                 |
|--------------------------|------------------------------------------|
| **Surefire (JUnit/XML)** | `target/surefire-reports/`               |
| **HTML Report**          | `target/site/` (via `mvn site`)          |
| **Console Output**       | Jenkins build console log                |

To generate an HTML site report locally:

```bash
mvn site
open target/site/index.html
```

In Jenkins, test results are typically published using the **JUnit Plugin** by pointing to `**/surefire-reports/*.xml`.

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository.
2. **Create** a new feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Commit** your changes with clear messages:
   ```bash
   git commit -m "feat: add test for DELETE endpoint"
   ```
4. **Push** to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Open a **Pull Request** against the `master` branch.

Please ensure all new tests pass locally before raising a PR.

---

## 📄 License

This project is open-source. Please refer to the repository for license details or contact the author.

---

## 👤 Author

**tech-prajwal**
- GitHub: [@tech-prajwal](https://github.com/tech-prajwal)
- Repository: [RestAssuredE2E](https://github.com/tech-prajwal/RestAssuredE2E)

---

> ⭐ If you find this project useful, consider giving it a star on [GitHub](https://github.com/tech-prajwal/RestAssuredE2E)!
