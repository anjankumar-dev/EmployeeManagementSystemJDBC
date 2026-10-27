# Employee Management System

A console-based Employee Management System built using **Java, JDBC, MySQL, and HikariCP**. The application performs employee CRUD operations, validates input, handles exceptions, and retrieves department-wise employee details using SQL JOINs.

## Features

- **Add Employee** — Register an employee with name, job title, salary, and department.
- **View All Employees** — Display all employee records.
- **Search Employee** — Find an employee using their ID.
- **Update Employee** — Modify existing employee details.
- **Delete Employee** — Delete an employee record with confirmation.
- **Department-Wise Display** — Retrieve employee details along with department names using an SQL JOIN.
- **Input Validation** — Validate employee data before database operations.
- **Exception Handling** — Handle invalid input, missing employees, and database errors.
- **Connection Pooling** — Manage database connections efficiently using HikariCP.

## Tech Stack

- **Language:** Java 17
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Connection Pool:** HikariCP
- **Build Tool:** Maven
- **SQL Concepts:** CRUD operations, PreparedStatement, ResultSet, Foreign Keys, INNER JOIN

## Project Structure

```text
src/main/java/org/anjan/
├── App.java
├── DBConnection.java
├── entity/
│   └── Employee.java
├── dao/
│   └── EmployeeDAO.java
├── service/
│   └── EmployeeService.java
└── exception/
    ├── EmployeeNotFoundException.java
    ├── EmployeeAlreadyExistsException.java
    └── InvalidEmployeeDataException.java
```

## Database Setup

Create the database and tables in MySQL:

```sql
CREATE DATABASE employee_management;
USE employee_management;

CREATE TABLE department (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO department (department_id, department_name)
VALUES
    (10, 'IT'),
    (20, 'HR'),
    (30, 'Finance'),
    (40, 'Marketing');

CREATE TABLE employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    job_title VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    department_id INT NOT NULL,
    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id)
        REFERENCES department(department_id)
);
```

## Configuration

Update the database credentials in `DBConnection.java`:

```java
config.setJdbcUrl(
    "jdbc:mysql://localhost:3306/employee_management"
);
config.setUsername("your_username");
config.setPassword("your_password");
```

Make sure MySQL is running and the configured user has permission to access the database.

Ensure that the Maven `pom.xml` includes the required dependencies for **MySQL Connector/J** and **HikariCP**.

## How to Run

1. Install JDK 17 or later, Maven, and MySQL.
2. Create the database and tables using the SQL script above.
3. Configure your MySQL username and password.
4. Open the project in IntelliJ IDEA, Eclipse, or another Java IDE.
5. Resolve Maven dependencies.
6. Run `App.java`.
7. Select an operation from the console menu.

## Application Menu

```text
===== Employee Management System =====
1. Add Employee
2. View All Employees
3. Search Employee by ID
4. Update Employee
5. Delete Employee
6. View Department-Wise Employees
0. Exit
```

Use department IDs **10, 20, 30, or 40** when adding or updating employees.

## Key Implementation Details

- **Entity Layer:** Represents employee data using the `Employee` class.
- **DAO Layer:** Encapsulates SQL queries and database access.
- **Service Layer:** Applies business validation and coordinates DAO operations.
- **Database Connection Layer:** Uses HikariCP to provide pooled JDBC connections.
- **Exception Layer:** Defines custom exceptions for invalid data and missing employees.

Parameterized queries with `PreparedStatement` help prevent SQL injection. Try-with-resources ensures JDBC resources are closed properly, allowing pooled connections to return to the pool.

## Learning Outcomes

This project demonstrates practical knowledge of Java OOP, JDBC, SQL CRUD operations, relational database design, foreign key constraints, SQL JOINs, connection pooling, layered architecture, input validation, and exception handling.

## Future Improvements

- Add employee email and enforce uniqueness.
- Implement transaction management where multiple database operations must succeed together.
- Add unit tests using JUnit and Mockito.
- Introduce logging and configuration through environment variables.
- Build a REST API using Spring Boot.

## Author

**Anjan Kumar Gogu**

GitHub: [anjan-kumar-dev](https://github.com/anjan-kumar-dev)
