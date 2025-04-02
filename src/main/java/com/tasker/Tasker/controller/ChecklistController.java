package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.Checklist;
import com.tasker.Tasker.service.ChecklistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/checklists")
public class ChecklistController {

    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @PostMapping
    public ResponseEntity<Checklist> createChecklist(@PathVariable Long taskId, @RequestBody Checklist checklist) {
        Checklist createdChecklist = checklistService.createChecklist(checklist.getTitle(), taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChecklist);
    }

    @GetMapping
    public ResponseEntity<List<Checklist>> getChecklistsByTask(@PathVariable Long taskId) {
        List<Checklist> checklists = checklistService.getChecklistsByTask(taskId);
        return ResponseEntity.ok(checklists);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Checklist>> getAllChecklists() {
        List<Checklist> checklists = checklistService.getAllChecklists();
        return ResponseEntity.ok(checklists);
    }

    @GetMapping("/{checklistId}")
    public ResponseEntity<Checklist> getChecklist(@PathVariable Long checklistId) {
        Checklist checklist = checklistService.getChecklist(checklistId);
        return ResponseEntity.ok(checklist);
    }

    @PutMapping("/{checklistId}")
    public ResponseEntity<Checklist> updateChecklist(@PathVariable Long checklistId, @RequestBody Checklist checklist) {
        Checklist updatedChecklist = checklistService.updateChecklist(checklistId, checklist.getTitle());
        return ResponseEntity.ok(updatedChecklist);
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long checklistId) {
        checklistService.deleteChecklist(checklistId);
        return ResponseEntity.noContent().build();
    }
}