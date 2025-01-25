# Quiz Backend

Welcome to the **Quiz Backend** project, a demo backend application developed for **Quad Solutions**. This project leverages modern Java development with **Spring Boot** to provide a robust and scalable foundation for quiz-related functionalities.

## Table of Contents

- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Building and Running the Project](#building-and-running-the-project)
- [Author and License](#author-and-license)

---

## Project Overview

The **Quiz Backend** is a Spring Boot application designed to serve as the backend of a quiz project. Its main purpose is to expose APIs capable of supporting quiz-related operations, making it suitable for dynamic and scalable quiz platforms.

This is a **Demo Project** aimed at demonstrating clean and maintainable backend architecture.

---

## Technologies Used

The project is built with the following stack:

- **Java 17** - Programming language for the project.
- **Spring Boot 3.4.1** - For rapid, production-ready application development.
  - Spring Web (REST APIs)
  - Spring Actuator (Monitoring and metrics)
- **Maven** - Dependency management and build tool.
- **Lombok** - Simplifies Java code with annotations.
- **Apache Commons Text** - For advanced string processing and utilities.

Dependencies and tools mentioned are defined in the `pom.xml`.

---

## Getting Started

### Prerequisites

Ensure you have the following installed:

1. Java 17+  
2. Maven 3.x  
3. An IDE (e.g., IntelliJ IDEA, Eclipse) for development.

### Clone the Repository

Clone the project using the following command in your terminal:

```bash
git clone <repository-url>
cd backend
```

> Replace `<repository-url>` with the actual URL of the project's repository.

---

## Building and Running the Project

### Build the Project

Run the following command to compile and package the application:

```bash
mvn clean install
```

This will create a packaged `.jar` file in the `target/` directory.

### Run the Application

To run the application, use the following Maven command:

```bash
mvn spring-boot:run
```

Alternatively, you can execute the `.jar` file directly:

```bash
java -jar target/quiz-backend-1.0.0-SNAPSHOT.jar
```

### Access the Application

Once the application starts, it will be available at: