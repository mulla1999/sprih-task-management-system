package com.sprih.task.management.system.validation;

import com.sprih.task.management.system.dto.TaskRequestVo;

import java.util.ArrayList;
import java.util.List;

public class TaskValidator {

    private TaskValidator() {
        //
    }

    private static boolean isNullOrEmpty(String value) {
        return (value == null || value.trim().isEmpty());
    }

    public static List<String> validate(TaskRequestVo taskRequestVo) {
        List<String> errorMessages = new ArrayList<>();

        if (isNullOrEmpty(taskRequestVo.getTitle())) {
            errorMessages.add("Task title cannot be null or empty.");
        }
        if (taskRequestVo.getStatus() == null) {
            errorMessages.add("Task status must be provided.");
        }
        if (taskRequestVo.getPriority() == null) {
            errorMessages.add("Task priority must be provided.");
        }

        return errorMessages;
    }

}
