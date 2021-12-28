package com.mvp.esm.exception;

import com.mvp.esm.controller.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ESMExceptionHandler {

@ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<String> employeeNotFoundHandler(EmployeeNotFoundException exception){
    return ResponseEntity.badRequest().body(exception.getMessage());
}

@ExceptionHandler(EmployeeValidationException.class)
    protected ResponseEntity<RestResponse> employeeValidationException(EmployeeValidationException exception){
    return ResponseEntity.badRequest().body(new RestResponse(exception.getMessage()));
}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
