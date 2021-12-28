package com.mvp.esm.exception;

public class EmployeeValidationException extends RuntimeException{
    public EmployeeValidationException() {
    }

    public EmployeeValidationException(String message) {
        super(message);
    }
}
