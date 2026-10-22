# Postman Testing Guide

Make sure the app is running on `http://localhost:8080` and MySQL is up.

## Option A - Import the collection (fastest)
1. Postman -> **Import** -> select `postman/Student-Management.postman_collection.json`.
2. Run the requests in order: **1. Register**, **2. Login**, then 3 to 7.
3. The Login request saves the token automatically, and the collection sends it as a Bearer token on the student requests.

## Option B - Manual steps

### 1. Register
- Method: `POST`
- URL: `http://localhost:8080/api/auth/register`
- Body -> raw -> JSON:
```json
{ "username": "testuser", "password": "test1234" }
```
- Expected: **201 Created**
```json
{ "message": "User registered successfully" }
```

### 2. Login
- Method: `POST`
- URL: `http://localhost:8080/api/auth/login`
- Body -> raw -> JSON: same as above
- Expected: **200 OK**
```json
{ "token": "eyJhbGciOi...", "type": "Bearer" }
```

### 3. Copy the JWT
Copy only the value of `token` (without the quotes).

### 4. Add the JWT to the next requests
For every student request: open the **Authorization** tab -> Type **Bearer Token** -> paste the token in the Token box.
(Or add a header manually: `Authorization` = `Bearer <token>`.)

### 5. Add student
- `POST http://localhost:8080/api/students`
- Body -> raw -> JSON:
```json
{ "name": "Rahul Sharma", "email": "rahul@example.com", "branch": "Computer Engineering", "year": 2 }
```
- Expected: **201 Created**, response includes `"id": 1`

### 6. Get all students
- `GET http://localhost:8080/api/students`
- Expected: **200 OK**, a JSON list of students

### 7. Get student by ID
- `GET http://localhost:8080/api/students/1`
- Expected: **200 OK**, the student with id 1

### 8. Update student
- `PUT http://localhost:8080/api/students/1`
- Body -> raw -> JSON:
```json
{ "name": "Rahul Sharma", "email": "rahul@example.com", "branch": "Information Technology", "year": 3 }
```
- Expected: **200 OK**, updated student

### 9. Delete student
- `DELETE http://localhost:8080/api/students/1`
- Expected: **200 OK**
```json
{ "message": "Student deleted successfully" }
```

## Extra checks (good for screenshots)
| Test | Expected |
|------|----------|
| `GET /api/students` with no token | 401 Unauthorized |
| `GET /api/students` with a wrong token | 401 Unauthorized |
| Login with wrong password | 401 Unauthorized |
| Register the same username twice | 400 Bad Request |
| `GET /api/students/999` | 404 Not Found |

## Screenshots to submit
Take one screenshot for each: Register, Login, Get Students, Get Student by ID, Add Student, Update Student, Delete Student.
