package com.sprih.task.management.system.repository;

import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setup() {
        task1 = Task.builder()
                .title("Task 1")
                .description("Description 1")
                .dueDate(LocalDate.now().plusDays(1))
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();

        task2 = Task.builder()
                .title("Task 2")
                .description("Description 2")
                .dueDate(LocalDate.now().plusDays(5))
                .priority(Priority.LOW)
                .status(Status.COMPLETED)
                .build();

        repository.save(task1);
        repository.save(task2);
    }

    @Test
    void shouldFindByStatus() {
        List<Task> pendingTasks = repository.findByStatus(Status.PENDING);
        assertThat(pendingTasks).hasSize(1);
        assertThat(pendingTasks.get(0).getTitle()).isEqualTo("Task 1");
    }

    @Test
    void shouldFindByPriority() {
        List<Task> highPriorityTasks = repository.findByPriority(Priority.HIGH);
        assertThat(highPriorityTasks).hasSize(1);
        assertThat(highPriorityTasks.get(0).getTitle()).isEqualTo("Task 1");
    }

    @Test
    void shouldFindByDueDateBetween() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(3);
        List<Task> tasksInRange = repository.findByDueDateBetween(start, end);

        assertThat(tasksInRange).hasSize(1);
        assertThat(tasksInRange.get(0).getTitle()).isEqualTo("Task 1");
    }

    @Test
    void shouldFindAll() {
        List<Task> allTasks = repository.findAll();
        assertThat(allTasks).hasSize(2);
    }
}
