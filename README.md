# Task Management System

## Overview
A Spring Boot RESTful API for managing tasks with support for future extensibility to handle users and projects. Includes filtering, pagination, sorting, DTO usage, and service-level logging.

## Features
- Create, update, delete, and retrieve tasks
- Uses DTO (`TaskRequestVo`) for input (security & clean separation from entity)
- Logs service operations with SLF4J (INFO, ERROR levels)
- Filter tasks by status, priority, or due date range
- Pagination and sorting on any field (default is priority)
- Exception handling with meaningful error responses
- In-memory H2 database for persistence
- Modular code structure for easy extensibility (e.g., Projects, Users)
- H2 Console enabled for direct database access

## Tech Stack
- Java 11
- Spring Boot 2.7.18
- Spring Data JPA
- SLF4J for Logging
- H2 In-Memory Database
- JUnit 5 for testing

## Getting Started

### Prerequisites
- Java 11
- Maven

### Running the Application

```bash
mvn clean spring-boot:run
```

### H2 Database Console
Access at: `http://localhost:9090/h2-console`  
JDBC URL: `jdbc:h2:mem:taskdb`  
Username: `sa`  
Password: *(leave blank)*

### API Endpoints

| Method | Endpoint         | Description            |
|--------|------------------|------------------------|
| POST   | `/api/tasks/add` | Create a new task (DTO)|
| GET    | `/api/tasks/filter` | Filter tasks by criteria |
| GET    | `/api/tasks/find/{id}` | Get task by ID         |
| PUT    | `/api/tasks/update/{id}` | Update task by ID (DTO) |
| DELETE | `/api/tasks/delete/{id}` | Delete task by ID      |
| GET    | `/api/tasks/paged` | Paginated & sorted list |

### Filtering Tasks Example

```bash
GET /api/tasks/filter?status=PENDING
GET /api/tasks/filter?priority=HIGH
GET /api/tasks/filter?start=2025-06-01&end=2025-07-01
```

### Pagination & Sorting Example

```bash
GET /api/tasks/paged?page=0&size=5&sortBy=dueDate&sortDir=asc
```

### Example Task Payload (POST/PUT)

```json
{
  "title": "Complete Assignment",
  "description": "Finish Spring Boot project",
  "dueDate": "2025-06-30",
  "priority": "HIGH",
  "status": "PENDING"
}
```

## Logging
- All service actions (`create`, `update`, `delete`, `findAll`, `findById`) are logged with SLF4J
- Logs include IDs, validation errors, and success confirmations
- Default logging to console (configurable)

## Future Enhancements
- Add User Management
- Add Project Management
- Authentication/Authorization (Spring Security)
- Response DTOs for output (optional future enhancement)
- Sorting on multiple fields

## Author
Developed by [Amir Mulla]