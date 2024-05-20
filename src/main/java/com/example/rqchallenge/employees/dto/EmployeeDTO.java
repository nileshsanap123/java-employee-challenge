package com.example.rqchallenge.employees.dto;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO implements Serializable {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private List<Employee> data;
    @JsonProperty("message")
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public List<Employee> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
