package com.sprih.task.management.system.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with ID " + id + " not found.");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
