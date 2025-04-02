package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final TaskService taskService;

    public ReportController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/project/{projectId}/tasks")
    public ResponseEntity<List<Task>> getProjectTasksReport(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }
}