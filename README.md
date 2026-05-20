# PetStore API Automation Framework

REST Assured + TestNG API automation framework for [Petstore Swagger API](https://petstore.swagger.io/).

## Project structure

```
src/test/java/api/
├── base/           # BaseApiTest — suite setup & shared constants
├── client/         # ApiClient — Rest Assured request builder
├── config/         # ConfigManager — environment configuration
├── endpoints/      # API endpoint classes (User, Store)
├── payload/        # Request/response POJOs
├── test/           # TestNG test classes
└── utilities/      # Reporting, data providers, validators

src/test/resources/
├── config/         # Environment properties (qa, staging, mock-server)
├── schemas/        # JSON Schema files
└── testdata/       # Excel test data (place testData.xlsx here)
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Jenkins (optional, for CI)

## Environments

| Environment   | Property file              | Default base URI                    |
|---------------|----------------------------|-------------------------------------|
| `qa`          | `config/qa.properties`     | `https://petstore.swagger.io/v2`    |
| `staging`     | `config/staging.properties`| Configurable staging URL            |
| `mock-server` | `config/mock-server.properties` | `http://localhost:8080/v2`   |

Activate with Maven: `-Denv=qa` (default), `-Denv=staging`, or `-Denv=mock-server`.

## Run tests locally

```bash
# Full regression (default) on QA
mvn clean test

# Smoke suite
mvn clean test -DsuiteXmlFile=testng-smoke.xml

# Store module only
mvn clean test -DsuiteXmlFile=testng-store.xml

# Staging environment
mvn clean test -Denv=staging

# Mock server (start WireMock/mock on port 8080 first)
mvn clean test -Denv=mock-server
```

## Test suites

| Suite file              | Description                                      |
|-------------------------|--------------------------------------------------|
| `testng.xml`            | Default — full regression                        |
| `testng-regression.xml` | User + Store + data-driven tests                 |
| `testng-smoke.xml`      | Critical path only (`smoke` group)               |
| `testng-store.xml`      | Store API tests only                             |

## Reports

- **Extent:** `Reports/API-Test-Report-<timestamp>.html`
- **TestNG:** `test-output/`
- **Surefire:** `target/surefire-reports/`

## Data-driven tests

Copy your Excel file to:

`src/test/resources/testdata/testData.xlsx`

Or legacy path: `testData/testData.xlsx` at project root.

## Jenkins CI

Use the included `Jenkinsfile`. The pipeline fails the build when any test fails.

**Parameters:**

- `ENV` — `qa`, `staging`, `mock-server`
- `SUITE` — `regression`, `smoke`, `store`

**Required Jenkins tools:** Maven + JDK 17 (configure in Global Tool Configuration).

## Maven profiles (optional)

```bash
mvn test -Pqa
mvn test -Pstaging
mvn test -Pmock-server
```
