package com.sprih.task.management.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sprih.task.management.system.exception.ExceptionConstants.MESSAGE;
import static com.sprih.task.management.system.exception.ExceptionConstants.TIMESTAMP;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException ex) {
        return new ResponseEntity<>(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTaskException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTask(InvalidTaskException ex) {
        return new ResponseEntity<>(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return new ResponseEntity<>(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }
}