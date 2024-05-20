package com.example.rqchallenge.employees.exception;

public class APIErrorResponse {
    private String message;

    public APIErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
