
-----

# Finance Tracker Backend

## 🌟 Project Overview

**Finance Tracker** is a modern personal finance management system designed as a full-stack application. The backend, built with **Spring Boot**, provides a robust, secure, and scalable REST API for managing financial transactions, analyzing income and expenses, and generating reports.

This project is a comprehensive showcase of the full development lifecycle—from API and database design to testing, containerization, and automated CI/CD.

_The frontend, based on Vue.js 3, is currently under active development._

## 🚀 Key API Features

* **Authentication & Security:** A reliable user registration and login system built with **JWT** (JSON Web Tokens) and **Spring Security**.
* **Transaction Management:** A full suite of CRUD operations (Create, Read, Update, Delete) for transactions, with filtering capabilities by date and category.
* **Real-time Analytics:** Endpoints to retrieve comprehensive financial analytics, including total balance, total income and expenses, and a breakdown by category.
* **Report Generation:** The ability to export financial data into **CSV** and **PDF** formats for monthly reporting.
* **Automatic Categorization (planned):** A feature to automatically assign a category to a transaction based on its description.

## 🛠️ Technology Stack

* **Framework:** Spring Boot 3.x
* **Security:** Spring Security, JWT (JJWT)
* **Database:** PostgreSQL 15-alpine
* **ORM:** Spring Data JPA (Hibernate)
* **Database Management:** Flyway for schema versioning and migrations.
* **Testing:** JUnit 5, Mockito, Spring Boot Test for unit and integration testing.
* **Documentation:** `springdoc-openapi-ui` for interactive API documentation.
* **Containerization:** Docker and Docker Compose for easy and isolated deployment.
* **Continuous Integration:** GitHub Actions for automated building, testing, and code validation.

## ⚙️ Getting Started

### 1\. Prerequisites

To run the backend, you'll need to have **Docker** and **Docker Compose** installed.

### 2\. Running the Application Locally

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/smerteliko/finance-tracker-backend-spring.git
    cd finance-tracker-backend-spring
    ```
2.  **Start the Docker containers:**
    ```bash
    docker-compose up --build
    ```
    This command will build the application image and start a PostgreSQL container for both the production and test environments, as well as the backend service.
3.  **Access the API:**
   * **Application:** `http://localhost:8080`
   * **Documentation (Swagger UI):** `http://localhost:8080/swagger-ui.html`

### 3\. Running Tests

You can run the integration tests locally using the test database configured in `docker-compose.yml`.

```bash
mvn test -Dspring.profiles.active=test
```

## 📈 Project Structure

The project is designed with a layered architecture, adhering to best practices:

* **`controller`**: Handles incoming HTTP requests and responses.
* **`services`**: Contains the core business logic of the application.
* **`repository`**: The data access layer that interacts with the database.
* **`entity`**: JPA entities representing the database tables.
* **`dto`**: Data Transfer Objects for communication between layers and the client.
* **`db/migration`**: Flyway scripts for database migrations.

## 🤝 Contributing

I welcome any suggestions and feedback for improvement. If you find a bug or want to propose a new feature, please create a **GitHub Issue** or a **Pull Request**.

-----

## 👨‍💻 Contact

* **Name:** Nikolay Makarov
* **GitHub:** https://github.com/smerteliko
* **LinkedIn:** https://www.linkedin.com/in/nikolay-makarov/