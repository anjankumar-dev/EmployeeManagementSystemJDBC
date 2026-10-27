package com.anjan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private int employeeId;
    private String name;
    private String jobTitle;
    private BigDecimal salary;
    private int departmentId;

    public Employee() {}

    public Employee(int employeeId, String name, String jobTitle, BigDecimal salary, int departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}
