package com.anjan;

import com.anjan.config.DataSourceProvider;
import com.anjan.entity.Employee;
import com.anjan.exceptions.EmployeeAlreadyExistException;
import com.anjan.service.EmployeeService;
import com.anjan.exceptions.EmployeeNotFoundException;
import com.anjan.exceptions.InvalidEmployeeDataException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeService service =
            new EmployeeService();

    public static void main(String[] args) {

        try {
            boolean running = true;

            while (running) {
                displayMenu();

                int choice = readInt("Enter your choice: ");

                try {
                    switch (choice) {
                        case 1 :{
                            addEmployee();
                            break;}
                        case 2 :{
                            viewAllEmployees();
                            break;}
                        case 3 : searchEmployee(); break;
                        case 4 : updateEmployee(); break;
                        case 5 : deleteEmployee(); break;
                        case 6 : viewDepartmentWise(); break;
                        case 7 : {
                            running = false;
                            System.out.println(
                                    "Exiting Employee Management System.");
                        }
                        default : System.out.println(
                                "Invalid choice. Please try again.");
                    }
                } catch (EmployeeNotFoundException e) {
                    System.out.println("Not found: " + e.getMessage());
                } catch (EmployeeAlreadyExistException e) {
                    System.out.println("Duplicate: " + e.getMessage());
                } catch (InvalidEmployeeDataException e) {
                    System.out.println("Invalid data: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Invalid number. Please enter a valid value.");
                } catch (SQLException e) {
                    System.out.println(
                            "Database error: " + e.getMessage());
                }
            }

        } finally {
            sc.close();
            DataSourceProvider.closePool();
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====\n" +
                "1. Add Employee\n" +
                "2. View All Employees\n" +
                "3. Search Employee By ID\n" +
                "4. Update Employee\n" +
                "5. Delete Employee\n" +
                "6. View Employees Department-wise\n" +
                "7. Exit");
    }

    private static void addEmployee() throws SQLException,
            EmployeeAlreadyExistException,InvalidEmployeeDataException {

        System.out.println("\n--- Add Employee ---");
        System.out.print("Enter employee id: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter job title: ");
        String jobTitle = sc.nextLine();

        BigDecimal salary = readSalary();

        int departmentId = readInt(
                "Enter department ID (10, 20, 30, 40): ");

        Employee employee = new Employee(
                employeeId,name, jobTitle, salary, departmentId);

        service.addEmployee(employee);

        System.out.println("Employee added successfully!");
    }

    private static void viewAllEmployees() throws SQLException {

        List<Employee> employees = service.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- Employee List ---");

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void searchEmployee() throws SQLException
    ,EmployeeNotFoundException,InvalidEmployeeDataException{

        int id = readInt("Enter employee ID: ");

        Employee employee = service.getEmployeeById(id);

        System.out.println(employee);
    }

    private static void updateEmployee() throws SQLException
    ,EmployeeNotFoundException,InvalidEmployeeDataException{

        int id = readInt("Enter employee ID to update: ");

        // Verify the employee exists before asking for new data.
        Employee existing = service.getEmployeeById(id);

        System.out.println("Current details: " + existing);
        System.out.println("Enter the new employee details:");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter job title: ");
        String jobTitle = sc.nextLine();

        BigDecimal salary = readSalary();

        int departmentId = readInt(
                "Enter department ID (10, 20, 30, 40): ");

        Employee updated = new Employee(
                id, name, jobTitle, salary, departmentId);

        service.updateEmployee(updated);

        System.out.println("Employee updated successfully!");
    }

    private static void deleteEmployee() throws SQLException,
            EmployeeNotFoundException,InvalidEmployeeDataException{

        int id = readInt("Enter employee ID to delete: ");

        // Ask for confirmation before permanently deleting.
        Employee employee = service.getEmployeeById(id);
        System.out.println("Selected employee: " + employee);

        System.out.print("Confirm deletion? (yes/no): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            service.deleteEmployee(id);
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void viewDepartmentWise() throws SQLException {

        List<Map<String, Object>> records =
                service.getEmployeesByDepartment();

        if (records.isEmpty()) {
            System.out.println("No department-wise records found.");
            return;
        }

        System.out.println("\n--- Employees Department-wise ---");

        for (Map<String, Object> row : records) {
            System.out.printf(
                    "Department: %s | ID: %s | Name: %s | "
                            + "Job: %s | Salary: %s%n",
                    row.get("departmentName"),
                    row.get("employeeId"),
                    row.get("name"),
                    row.get("jobTitle"),
                    row.get("salary")
            );
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(sc.nextLine());
    }

    private static BigDecimal readSalary() {
        System.out.print("Enter salary: ");
        return new BigDecimal(sc.nextLine());
    }
}
