package com.example.rqchallenge.employees.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Employee implements Serializable {

    @JsonProperty("salary")
    private int salary;
    private String profileImage;
    private int id;
    @JsonProperty("age")
    private int employeeAge;

    public Employee() {
    }
    public Employee(String employeeName, int salary, String profileImage, int id, int employeeAge) {
        this.employeeName = employeeName;
        this.salary = salary;
        this.profileImage = profileImage;
        this.id = id;
        this.employeeAge = employeeAge;
    }

    @JsonProperty("name")
    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

}
