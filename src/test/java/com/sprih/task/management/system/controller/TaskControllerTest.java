package com.sprih.task.management.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprih.task.management.system.dto.TaskRequestVo;
import com.sprih.task.management.system.model.Priority;
import com.sprih.task.management.system.model.Status;
import com.sprih.task.management.system.model.Task;
import com.sprih.task.management.system.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;
    private TaskRequestVo taskRequestVo;

    @BeforeEach
    void setUp() {
        taskRequestVo = TaskRequestVo.builder()
                .title("Test Task")
                .description("Test Description")
                .dueDate(LocalDate.now().plusDays(1))
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();

        task = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .dueDate(LocalDate.now().plusDays(1))
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .build();
    }

    @Test
    void shouldCreateTask() throws Exception {
        when(taskService.create(any(TaskRequestVo.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequestVo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(taskService.update(eq(1L), any(TaskRequestVo.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequestVo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        when(taskService.findById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void shouldListTasks() throws Exception {
        when(taskService.findAll(any(), any(), any(), any())).thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/api/tasks/delete/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).delete(1L);
    }

    @Test
    void shouldGetPagedTasks() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> page = new PageImpl<>(List.of(task), pageable, 1);

        when(taskService.getPagedTasks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/tasks/paged?page=0&size=10&sortBy=priority&sortDir=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService, times(1)).getPagedTasks(any(Pageable.class));
    }
}