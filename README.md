# Student Management Backend

Full Stack Java Programming Lab - Assignment 2

A simple REST API built with **Spring Boot, Spring Security (JWT), Spring Data JPA and MySQL**.
Users can register and log in. After login they receive a JWT token, which is required to use the student APIs.

## Tech Stack

- Java 17 (works with Java 21 too)
- Spring Boot 3.3.5 (Web, Data JPA, Security)
- MySQL + Hibernate
- JWT (jjwt 0.12.6) and BCrypt password hashing
- Maven
- Postman (testing)

## Project Structure

```
student-management/
├── pom.xml
├── README.md
├── database/schema.sql
├── postman/
│   ├── Student-Management.postman_collection.json
│   └── POSTMAN_TESTING.md
└── src/main/
    ├── java/com/college/studentmanagement/
    │   ├── StudentManagementApplication.java
    │   ├── controller/   AuthController, StudentController
    │   ├── service/      StudentService
    │   ├── repository/   UserRepository, StudentRepository
    │   ├── entity/       User, Student
    │   ├── dto/          AuthRequest
    │   └── security/     SecurityConfig, JwtAuthFilter, JwtUtil, CustomUserDetailsService
    └── resources/application.properties
```

## Setup

### 1. Requirements
- JDK 17 or 21
- Maven 3.8+ (or open the project in IntelliJ / Eclipse / VS Code, which include Maven)
- MySQL 8 running on port 3306

### 2. Database
The app creates the `student_management` database and tables automatically.
To create them manually instead, run:

```
mysql -u YOUR_USERNAME -p < database/schema.sql
```

### 3. Configure `src/main/resources/application.properties`
Replace the placeholders with your own MySQL login:

```
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Optionally change `jwt.secret` (must be at least 32 characters).

### 4. Run

```
mvn clean install
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

## API Endpoints

| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| POST | /api/auth/register | No | Register a new user |
| POST | /api/auth/login | No | Login and get a JWT token |
| POST | /api/students | JWT | Add a student |
| GET | /api/students | JWT | View all students |
| GET | /api/students/{id} | JWT | View student by ID |
| PUT | /api/students/{id} | JWT | Update a student |
| DELETE | /api/students/{id} | JWT | Delete a student |

Send the token in the header: `Authorization: Bearer <token>`

Without a valid token the student APIs return **401 Unauthorized**.

## Example Request Bodies

Register / Login:
```json
{ "username": "testuser", "password": "test1234" }
```

Add / Update student:
```json
{ "name": "Rahul Sharma", "email": "rahul@example.com", "branch": "Computer Engineering", "year": 2 }
```

## Testing

See [postman/POSTMAN_TESTING.md](postman/POSTMAN_TESTING.md) for step-by-step Postman instructions.
You can also import `postman/Student-Management.postman_collection.json` into Postman.

## How Security Works

1. `/register` saves the user with a BCrypt-hashed password.
2. `/login` checks the credentials and returns a signed JWT.
3. `JwtAuthFilter` reads the `Authorization` header on every request, validates the token and marks the user as logged in.
4. `SecurityConfig` allows only `/api/auth/**` publicly; everything else needs a valid token.
