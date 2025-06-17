package com.sprih.task.management.system.validation;

import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskValidatorTest {

    @Test
    void shouldReturnNoErrorsForValidTask() {
        TaskRequestVo validTask = TaskRequestVo.builder()
                .title("Valid Task")
                .description("Description")
                .dueDate(LocalDate.now().plusDays(2))
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();

        List<String> errors = TaskValidator.validate(validTask);

        assertThat(errors).isEmpty();
    }

    @Test
    void shouldReturnErrorsForNullTitle() {
        TaskRequestVo invalidTask = TaskRequestVo.builder()
                .title(null)
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();

        List<String> errors = TaskValidator.validate(invalidTask);

        assertThat(errors).contains("Task title cannot be null or empty.");
    }

    @Test
    void shouldReturnErrorsForEmptyTitle() {
        TaskRequestVo invalidTask = TaskRequestVo.builder()
                .title("   ")
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();

        List<String> errors = TaskValidator.validate(invalidTask);

        assertThat(errors).contains("Task title cannot be null or empty.");
    }

    @Test
    void shouldReturnErrorForNullPriority() {
        TaskRequestVo invalidTask = TaskRequestVo.builder()
                .title("Valid")
                .priority(null)
                .status(Status.PENDING)
                .build();

        List<String> errors = TaskValidator.validate(invalidTask);

        assertThat(errors).contains("Task priority must be provided.");
    }

    @Test
    void shouldReturnErrorForNullStatus() {
        TaskRequestVo invalidTask = TaskRequestVo.builder()
                .title("Valid")
                .priority(Priority.HIGH)
                .status(null)
                .build();

        List<String> errors = TaskValidator.validate(invalidTask);

        assertThat(errors).contains("Task status must be provided.");
    }

    @Test
    void shouldReturnMultipleErrors() {
        TaskRequestVo invalidTask = TaskRequestVo.builder()
                .title("")
                .priority(null)
                .status(null)
                .build();

        List<String> errors = TaskValidator.validate(invalidTask);

        assertThat(errors).containsExactlyInAnyOrder(
                "Task title cannot be null or empty.",
                "Task status must be provided.",
                "Task priority must be provided."
        );
    }
}
