package com.example.rqchallenge.employees.dto;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateEmployeeResponseDto {
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private Employee data;
    @JsonProperty("message")
    private String message;

    public CreateEmployeeResponseDto(String status, Employee data) {
        this.status = status;
        this.data = data;
    }

    public CreateEmployeeResponseDto(String status, Employee data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public CreateEmployeeResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
