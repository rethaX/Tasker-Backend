package com.tasker.Tasker.controller;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.model.TaskRequest;
import com.tasker.Tasker.service.TaskService;
import com.tasker.Tasker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> createTask(@PathVariable Long projectId, @Valid @RequestBody TaskRequest taskRequest) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username);
        Task task = taskService.createTask(taskRequest.getTitle(), taskRequest.getDescription(), projectId,
                taskRequest.getListId(), taskRequest.getEpicId(), taskRequest.getSprintId(), user.getId(), taskRequest.getDueDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PostMapping("/tasks/bulk")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> createBulkTasks(@Valid @RequestBody List<TaskRequest> tasks) {
        List<Task> createdTasks = taskService.createBulkTasks(tasks);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTasks);
    }

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getAllTasks(status, priority, assigneeId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest) {
        Task updatedTask = taskService.updateTask(taskId, taskRequest.getTitle(), taskRequest.getDescription(),
                taskRequest.getStatus(), taskRequest.getPriority(), taskRequest.getDueDate());
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/tasks/{taskId}/assign")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @RequestParam Long userId) {
        Task assignedTask = taskService.assignTask(taskId, userId);
        return ResponseEntity.ok(assignedTask);
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String q) {
        List<Task> tasks = taskService.searchTasks(q);
        return ResponseEntity.ok(tasks);
    }
}