package com.service.parcelmanagement.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(buildError("Validation failed", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String field = cv.getPropertyPath().toString();
            field = field.substring(field.lastIndexOf('.') + 1);
            errors.put(field, cv.getMessage());
        });
        return ResponseEntity.unprocessableEntity().body(buildError("Validation failed", errors));
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<?> handleTypeMismatch(Exception ex) {
        String message = "Invalid input format";
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mismatch = (MethodArgumentTypeMismatchException) ex;
            message = "Parameter '" + mismatch.getName() + "' should be of type " +
                    (mismatch.getRequiredType() != null ? mismatch.getRequiredType().getSimpleName() : "Unknown");
        } else if (ex.getCause() instanceof DateTimeParseException) {
            message = "Invalid date format. Expected format: yyyy-MM-dd";
        }

        return ResponseEntity.unprocessableEntity().body(buildError(message, null));
    }

    private Map<String, Object> buildError(String msg, Map<String, String> details) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", 422);
        error.put("error", msg);
        if (details != null) error.put("details", details);
        return error;
    }
}
