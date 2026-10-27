package org.anjan.service;

import org.anjan.dao.EmployeeDAO;
import org.anjan.entity.Employee;
import org.anjan.exceptions.EmployeeNotFoundException;
import org.anjan.exceptions.InvalidEmployeeDataException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAO();

    private void validate(Employee employee) throws InvalidEmployeeDataException {
        if (employee == null) {
            throw new InvalidEmployeeDataException(
                    "Employee data is required");
        }

        if (employee.getName() == null) {
            throw new InvalidEmployeeDataException(
                    "Employee name cannot be empty");
        }

        if (employee.getJobTitle() == null) {
            throw new InvalidEmployeeDataException(
                    "Job title cannot be empty");
        }

        if (employee.getSalary() == null
                || employee.getSalary().signum() < 0) {
            throw new InvalidEmployeeDataException(
                    "Salary cannot be negative");
        }

        if (employee.getDepartmentId() <= 0) {
            throw new InvalidEmployeeDataException(
                    "Department ID must be positive");
        }
    }

    public void addEmployee(Employee employee)
            throws SQLException, InvalidEmployeeDataException {
        validate(employee);

        int rows = dao.addEmployee(employee);

        if (rows != 1) {
            throw new SQLException("Employee insertion failed");
        }
    }

    public List<Employee> getAllEmployees()
            throws SQLException {
        return dao.getAllEmployees();
    }

    public Employee getEmployeeById(int employeeId)
            throws SQLException,EmployeeNotFoundException,InvalidEmployeeDataException {
        if (employeeId <= 0) {
            throw new InvalidEmployeeDataException(
                    "Employee ID must be positive");
        }

        Employee employee = dao.getEmployeeById(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Employee not found with ID: " + employeeId);
        }

        return employee;
    }

    public void updateEmployee(Employee employee)
            throws SQLException,EmployeeNotFoundException,InvalidEmployeeDataException {
        validate(employee);

        if (employee.getEmployeeId() <= 0) {
            throw new InvalidEmployeeDataException(
                    "Employee ID must be positive");
        }

        // Confirm the employee exists before updating.
        getEmployeeById(employee.getEmployeeId());

        int rows = dao.updateEmployee(employee);

        if (rows == 0) {
            // The record may have been unchanged or deleted
            // concurrently after the existence check.
            // A zero count alone doesn't prove it is missing.
            Employee current =
                    dao.getEmployeeById(employee.getEmployeeId());

            if (current == null) {
                throw new EmployeeNotFoundException(
                        "Employee not found with ID: "
                                + employee.getEmployeeId());
            }
        }
    }

    public void deleteEmployee(int employeeId)
            throws SQLException,EmployeeNotFoundException,InvalidEmployeeDataException {
        getEmployeeById(employeeId);

        int rows = dao.deleteEmployee(employeeId);

        if (rows == 0) {
            throw new EmployeeNotFoundException(
                    "Employee not found with ID: " + employeeId);
        }
    }

    public List<Map<String, Object>> getEmployeesByDepartment()
            throws SQLException {
        return dao.getEmployeesByDepartment();
    }

}
