package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.Sprint;
import com.tasker.Tasker.model.SprintRequest;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.service.SprintService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Sprint> createSprint(@Valid @RequestBody SprintRequest sprintRequest) {
        Sprint sprint = sprintService.createSprint(sprintRequest.getName(), sprintRequest.getStartDate(),
                sprintRequest.getEndDate(), sprintRequest.getProjectId());
        return ResponseEntity.status(HttpStatus.CREATED).body(sprint);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Sprint>> getAllSprints() {
        List<Sprint> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(sprints);
    }

    @GetMapping("/{sprintId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Sprint> getSprint(@PathVariable Long sprintId) {
        Sprint sprint = sprintService.getSprint(sprintId);
        return ResponseEntity.ok(sprint);
    }

    @GetMapping("/{sprintId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getSprintTasks(@PathVariable Long sprintId) {
        Sprint sprint = sprintService.getSprint(sprintId);
        List<Task> tasks = sprint.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{sprintId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Sprint> updateSprint(@PathVariable Long sprintId, @Valid @RequestBody SprintRequest sprintRequest) {
        Sprint updatedSprint = sprintService.updateSprint(sprintId, sprintRequest.getName(),
                sprintRequest.getStartDate(), sprintRequest.getEndDate());
        return ResponseEntity.ok(updatedSprint);
    }

    @PostMapping("/{sprintId}/start")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sprint> startSprint(@PathVariable Long sprintId) {
        Sprint startedSprint = sprintService.startSprint(sprintId);
        return ResponseEntity.ok(startedSprint);
    }

    @PostMapping("/{sprintId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sprint> completeSprint(@PathVariable Long sprintId) {
        Sprint completedSprint = sprintService.completeSprint(sprintId);
        return ResponseEntity.ok(completedSprint);
    }

    @DeleteMapping("/{sprintId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long sprintId) {
        sprintService.deleteSprint(sprintId);
        return ResponseEntity.noContent().build();
    }
}