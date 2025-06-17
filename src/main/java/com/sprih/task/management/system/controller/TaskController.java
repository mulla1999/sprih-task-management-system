package com.sprih.task.management.system.controller;


import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import com.sprih.task.management.system.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Task createTask(@RequestBody TaskRequestVo taskRequestVo) {
        return service.create(taskRequestVo);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskRequestVo taskRequestVo) {
        return service.update(id, taskRequestVo);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/filter")
    public List<Task> listTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return service.findAll(status, priority, start, end);
    }

    @GetMapping("/find/{id}")
    public Task getTask(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/paged")
    public Page<Task> getPagedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.getPagedTasks(pageable);
    }

}
