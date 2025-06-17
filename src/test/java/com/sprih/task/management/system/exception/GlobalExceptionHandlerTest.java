package com.sprih.task.management.system.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleTaskNotFound() {
        TaskNotFoundException ex = new TaskNotFoundException(1L);
        ResponseEntity<?> response = handler.handleTaskNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertNotNull(response.getBody());
        assertThat((Map<String, Object>) response.getBody()).containsEntry("message", "Task with ID 1 not found.");
        assertThat(((Map<?, ?>) response.getBody()).get("timestamp")).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void shouldHandleInvalidTask() {
        InvalidTaskException ex = new InvalidTaskException("Invalid task");
        ResponseEntity<?> response = handler.handleInvalidTask(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertNotNull(response.getBody());
        assertThat((Map<String, Object>) response.getBody()).containsEntry("message", "Invalid task");
        assertThat(((Map<?, ?>) response.getBody()).get("timestamp")).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void shouldHandleValidationErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("objectName", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<?> response = handler.handleValidationErrors(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertNotNull(response.getBody());
        assertThat(((Map<?, ?>) response.getBody()).get("message")).asList().contains("field: must not be null");
        assertThat(((Map<?, ?>) response.getBody()).get("timestamp")).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected error");
        ResponseEntity<?> response = handler.handleGeneric(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertNotNull(response.getBody());
        assertThat((Map<String, Object>) response.getBody()).containsEntry("message", "Unexpected error");
        assertThat(((Map<?, ?>) response.getBody()).get("timestamp")).isInstanceOf(LocalDateTime.class);
    }
}
