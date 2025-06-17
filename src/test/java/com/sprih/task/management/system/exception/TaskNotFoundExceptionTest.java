package com.sprih.task.management.system.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithId() {
        TaskNotFoundException exception = new TaskNotFoundException(10L);
        assertThat(exception.getMessage()).isEqualTo("Task with ID 10 not found.");
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        TaskNotFoundException exception = new TaskNotFoundException("Custom error message");
        assertThat(exception.getMessage()).isEqualTo("Custom error message");
    }
}
