package org.anjan.dao;

import org.anjan.config.DataSourceProvider;
import org.anjan.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {

    // 1. INSERT
    public int addEmployee(Employee employee)
            throws SQLException {

        String sql = "INSERT INTO employee(emp_Id,name, job_title, salary, dept_id)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,employee.getEmployeeId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getJobTitle());
            ps.setBigDecimal(4, employee.getSalary());
            ps.setInt(5, employee.getDepartmentId());

            return ps.executeUpdate();
        }
    }

    // 2. SELECT ALL
    public List<Employee> getAllEmployees()
            throws SQLException {

        String query = "SELECT * FROM employee ORDER BY emp_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             Statement s = con.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        }

        return employees;
    }

    // 3. SELECT BY ID
    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        String sql = " SELECT * FROM employee WHERE emp_id = ?";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        }

        return null;
    }

    // 4. UPDATE
    public int updateEmployee(Employee employee)
            throws SQLException {

        String sql = " UPDATE employee SET " +
                "name = ?, job_title = ?, salary = ?, dept_id = ? " +
                "WHERE emp_id = ? ";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getJobTitle());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, employee.getEmployeeId());

            return ps.executeUpdate();
        }
    }

    // 5. DELETE
    public int deleteEmployee(int employeeId)
            throws SQLException {

        String sql = " DELETE FROM employee WHERE emp_id = ?";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            return ps.executeUpdate();
        }
    }

    // 6. DEPARTMENT-WISE JOIN
    public List<Map<String, Object>> getEmployeesByDepartment()
            throws SQLException {

        String sql = "SELECT e.emp_id, e.name, e.job_title, " +
                "e.salary, d.dept_id, d.dept_name " +
                "FROM employee e " +
                "INNER JOIN department d ON e.dept_id = d.dept_id " +
                "ORDER BY d.dept_name, e.name";

        List<Map<String, Object>> records = new ArrayList<>();

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();

                row.put("employeeId", rs.getInt("emp_id"));
                row.put("name", rs.getString("name"));
                row.put("jobTitle", rs.getString("job_title"));
                row.put("salary", rs.getBigDecimal("salary"));
                row.put("departmentId", rs.getInt("dept_id"));
                row.put("departmentName",
                        rs.getString("dept_name"));

                records.add(row);
            }
        }

        return records;
    }

    // Convert a ResultSet row into an Employee object
    private Employee mapEmployee(ResultSet rs)
            throws SQLException {

        Employee employee = new Employee();

        employee.setEmployeeId(rs.getInt("emp_id"));
        employee.setName(rs.getString("name"));
        employee.setJobTitle(rs.getString("job_title"));
        employee.setSalary(rs.getBigDecimal("salary"));
        employee.setDepartmentId(rs.getInt("dept_id"));

        return employee;
    }
}