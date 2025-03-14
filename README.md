# OCR API Test Suite

This project is a comprehensive suite of tests for validating the functionality of an OCR (Optical Character Recognition) API. The tests ensure that the API works as expected under various scenarios such as invalid API keys, file size limits, and unsupported file formats. The tests are written in **Java** using **Rest Assured**, **TestNG**, and **Maven**.

## Test Scenarios

The following scenarios are tested to ensure the proper functionality of the OCR API:

- **Invalid API Key**: Verifying that the OCR API responds correctly when an invalid API key is provided.
- **No API Key**: Verifying that the OCR API responds with an appropriate error when no API key is provided in the request.
- **Empty API Key**: Verifying that the OCR API responds with an error when the API key is provided but is empty.
- **Valid API Key**: Verifying that the OCR API correctly processes requests when a valid API key is provided.
- **Large Image File**: Ensuring that the OCR API rejects image files that exceed the maximum size limit (e.g., 1024 KB).
- **Small Image File**: Testing that the OCR API handles small image files and processes them as expected.
- **Unsupported File Format**: Verifying that the OCR API handles unsupported file formats correctly (e.g., text files or non-image files).
- **Standard Image File**: Ensuring that the OCR API correctly processes and returns expected results for standard image files.

---

## Tools Used

The project leverages the following tools and libraries:

- **Rest Assured**: For performing API tests and sending requests.
- **TestNG**: Used for organizing and running tests. Features like **DataProvider** and **Data Interceptors** are utilized for parameterized tests and managing test execution.
- **Log4j2**: For logging detailed information during test execution.
- **Apache POI**: For reading and writing Excel data for the tests.
- **Extent Reports**: For generating HTML reports after each test run, which provides an in-depth view of the test results.

## Features

- **Multi-threaded Test Execution**: Ensures test independence using `ThreadLocal` for log and report isolation.
- **IDE & Command Line Support**: The project is configured to run in both an IDE and as a Maven build from the command line.
- **HTML Reports**: Detailed reports are generated for every test execution using ExtentReports, providing an easy-to-read format.
- **Data-Driven Tests**: Test data is provided through Excel files and TestNG DataProviders, which enable running the same tests with different inputs.

## How to Run

### Prerequisites

Ensure that the following are set up:

- **JDK 17** or higher installed
- **Maven** installed
- **Rest Assured** dependencies are available in the `pom.xml`
- **TestNG** dependencies are included in the `pom.xml`

### Running in IDE

1. **Import the project** into your IDE (e.g., IntelliJ IDEA or Eclipse).
2. Ensure all dependencies are downloaded (Maven will handle it).
3. Right-click on the TestNG class and run it.

### Running via Command Line (Maven)

1. Open a terminal and navigate to the project directory.
2. Run the tests using Maven:

```bash
mvn clean test
