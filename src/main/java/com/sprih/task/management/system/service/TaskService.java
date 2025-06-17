package com.sprih.task.management.system.service;

import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task create(TaskRequestVo taskRequestVo);

    Task update(Long id, TaskRequestVo taskRequestVo);

    void delete(Long id);

    List<Task> findAll(Status status, Priority priority, LocalDate start, LocalDate end);

    Task findById(Long id);

    Page<Task> getPagedTasks(Pageable pageable);
}