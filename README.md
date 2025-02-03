# Homework Backend

This is the backend for the Helmes homework project. It is built using Java 21, Spring Boot, Spring Security, Spring Data JPA, and Flyway.

## Features
- **REST API** for managing sectors and user data
- **Basic authentication** for securing the API
- **Database migrations** with Flyway
- **H2 in-memory database**
- **Integration and unit tests**

## Prerequisites
- Java 21
- Maven

## Installation and Running the Project

### 1. Clone the Repository
```sh
$ git clone <repository-url>
$ cd homework-backend
```

### 2. Build the Project
```sh
$ mvn clean install
```

### 3. Run the Application
```sh
$ mvn spring-boot:run
```
The backend will start on `http://localhost:8080`.

### 4. Accessing the H2 console
The H2 database console is available at:
```
http://localhost:8080/h2-console
```
Use the credentials from `application.yml`.

## API endpoints

### Sectors
- **GET** `/api/sectors` - Retrieve all sectors.

### User Data
- **POST** `/api/user-data` - Create a new user data record.
- **PUT** `/api/user-data/{id}` - Update an existing user data record.

## Authentication
The API is secured with **Basic authentication**. The credentials are stored in `application.yml`.


To make authenticated requests, include an `Authorization` header:
```
Authorization: Basic base64(username:password)
```

## Running tests
To run the unit and integration tests:
```sh
$ mvn test
```

## Database schema
The database consists of the following tables:
- **`sector`** - Stores sector information, supporting a hierarchical parent-child structure.
- **`user_data`** - Stores user information.
- **`user_sector`** - Many-to-many relationship between users and sectors.

## Technologies used
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Flyway
- H2
- Maven
- JUnit & Mockito (for testing)
