package com.sprih.task.management.system.dto;

import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestVo {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;
}