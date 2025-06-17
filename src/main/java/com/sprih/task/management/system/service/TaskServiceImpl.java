package com.sprih.task.management.system.service;

import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.exception.InvalidTaskException;
import com.sprih.task.management.system.exception.TaskNotFoundException;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import com.sprih.task.management.system.repository.TaskRepository;
import com.sprih.task.management.system.validation.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task create(TaskRequestVo taskRequestVo) {
        logger.info("Creating new task: {}", taskRequestVo.getTitle());

        if (taskRequestVo.getStatus() == null) {
            taskRequestVo.setStatus(Status.PENDING);
        }

        List<String> validationErrors = TaskValidator.validate(taskRequestVo);
        if (!validationErrors.isEmpty()) {
            logger.error("Validation failed for new task: {}", validationErrors);
            throw new InvalidTaskException(String.join(" ", validationErrors));
        }

        Task task = Task.builder()
                .title(taskRequestVo.getTitle())
                .description(taskRequestVo.getDescription())
                .dueDate(taskRequestVo.getDueDate())
                .priority(taskRequestVo.getPriority())
                .status(taskRequestVo.getStatus())
                .build();

        Task savedTask = repository.save(task);
        logger.info("Task created successfully with ID: {}", savedTask.getId());
        return savedTask;
    }

    @Override
    public Task update(Long id, TaskRequestVo taskRequestVo) {
        logger.info("Updating task with ID: {}", id);

        Task existing = repository.findById(id).orElseThrow(() -> {
            logger.error("Task not found for update with ID: {}", id);
            return new TaskNotFoundException(id);
        });

        List<String> validationErrors = TaskValidator.validate(taskRequestVo);
        if (!validationErrors.isEmpty()) {
            logger.error("Validation failed for task update ID {}: {}", id, validationErrors);
            throw new InvalidTaskException(String.join(" ", validationErrors));
        }

        existing.setTitle(taskRequestVo.getTitle());
        existing.setDescription(taskRequestVo.getDescription());
        existing.setDueDate(taskRequestVo.getDueDate());
        existing.setPriority(taskRequestVo.getPriority());
        existing.setStatus(taskRequestVo.getStatus());

        Task updatedTask = repository.save(existing);
        logger.info("Task updated successfully with ID: {}", updatedTask.getId());
        return updatedTask;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting task with ID: {}", id);

        repository.findById(id).orElseThrow(() -> {
            logger.error("Task not found for deletion with ID: {}", id);
            return new TaskNotFoundException(id);
        });

        repository.deleteById(id);
        logger.info("Task deleted successfully with ID: {}", id);
    }

    @Override
    public List<Task> findAll(Status status, Priority priority, LocalDate start, LocalDate end) {
        logger.info("Fetching tasks with filters - status: {}, priority: {}, start: {}, end: {}", status, priority, start, end);

        if (status != null) return repository.findByStatus(status);
        if (priority != null) return repository.findByPriority(priority);
        if (start != null && end != null) return repository.findByDueDateBetween(start, end);
        return repository.findAll();
    }

    @Override
    public Task findById(Long id) {
        logger.info("Fetching task with ID: {}", id);
        return repository.findById(id).orElseThrow(() -> {
            logger.error("Task not found with ID: {}", id);
            return new TaskNotFoundException(id);
        });
    }

    @Override
    public Page<Task> getPagedTasks(Pageable pageable) {
        logger.info("Fetching paginated tasks - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable);
    }
}
