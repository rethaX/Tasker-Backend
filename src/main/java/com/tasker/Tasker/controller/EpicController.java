package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.Epic;
import com.tasker.Tasker.service.EpicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/epics")
public class EpicController {

    private final EpicService epicService;

    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping
    public ResponseEntity<Epic> createEpic(@PathVariable Long projectId, @RequestBody Epic epic) {
        Epic createdEpic = epicService.createEpic(epic.getName(), epic.getDescription(), projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEpic);
    }

    @GetMapping
    public ResponseEntity<List<Epic>> getEpicsByProject(@PathVariable Long projectId) {
        List<Epic> epics = epicService.getEpicsByProject(projectId);
        return ResponseEntity.ok(epics);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Epic>> getAllEpics() {
        List<Epic> epics = epicService.getAllEpics();
        return ResponseEntity.ok(epics);
    }

    @GetMapping("/{epicId}")
    public ResponseEntity<Epic> getEpic(@PathVariable Long epicId) {
        Epic epic = epicService.getEpic(epicId);
        return ResponseEntity.ok(epic);
    }

    @PutMapping("/{epicId}")
    public ResponseEntity<Epic> updateEpic(@PathVariable Long epicId, @RequestBody Epic epic) {
        Epic updatedEpic = epicService.updateEpic(epicId, epic.getName(), epic.getDescription());
        return ResponseEntity.ok(updatedEpic);
    }

    @DeleteMapping("/{epicId}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long epicId) {
        epicService.deleteEpic(epicId);
        return ResponseEntity.noContent().build();
    }
}