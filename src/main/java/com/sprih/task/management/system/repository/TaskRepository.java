package com.sprih.task.management.system.repository;


import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByDueDateBetween(LocalDate start, LocalDate end);
}
