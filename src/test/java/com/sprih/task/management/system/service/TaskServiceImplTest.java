package com.sprih.task.management.system.service;

import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.exception.InvalidTaskException;
import com.sprih.task.management.system.exception.TaskNotFoundException;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import com.sprih.task.management.system.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task;
    private TaskRequestVo request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        request = TaskRequestVo.builder()
                .title("Test Task")
                .description("Description")
                .dueDate(LocalDate.now().plusDays(1))
                .priority(Priority.MEDIUM)
                .status(Status.PENDING)
                .build();

        task = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Description")
                .dueDate(LocalDate.now().plusDays(1))
                .priority(Priority.MEDIUM)
                .status(Status.PENDING)
                .build();
    }

    @Test
    void shouldCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.create(request);

        assertThat(result.getId()).isEqualTo(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldThrowInvalidTaskExceptionForCreate() {
        TaskRequestVo invalid = TaskRequestVo.builder().title("").build();

        assertThatThrownBy(() -> taskService.create(invalid))
                .isInstanceOf(InvalidTaskException.class);
    }

    @Test
    void shouldUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.update(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldThrowInvalidTaskExceptionForUpdate() {
        TaskRequestVo invalid = TaskRequestVo.builder().title("").build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> taskService.update(1L, invalid))
                .isInstanceOf(InvalidTaskException.class);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionForUpdate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.update(1L, request))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionForDelete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.delete(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldFindTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task found = taskService.findById(1L);

        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionForFindById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldGetPagedTasks() {
        Page<Task> page = new PageImpl<>(List.of(task));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Task> result = taskService.getPagedTasks(PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1);
    }


    @Test
    void shouldFindByStatus() {
        when(taskRepository.findByStatus(Status.PENDING)).thenReturn(List.of(task));
        List<Task> result = taskService.findAll(Status.PENDING, null, null, null);
        assertThat(result).hasSize(1).contains(task);
    }

    @Test
    void shouldFindByPriority() {
        when(taskRepository.findByPriority(Priority.HIGH)).thenReturn(List.of(task));
        List<Task> result = taskService.findAll(null, Priority.HIGH, null, null);
        assertThat(result).hasSize(1).contains(task);
    }

    @Test
    void shouldFindByDueDateBetween() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        when(taskRepository.findByDueDateBetween(start, end)).thenReturn(List.of(task));
        List<Task> result = taskService.findAll(null, null, start, end);
        assertThat(result).hasSize(1).contains(task);
    }

    @Test
    void shouldFindAllWhenNoFiltersProvided() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<Task> result = taskService.findAll(null, null, null, null);
        assertThat(result).hasSize(1).contains(task);
    }
}