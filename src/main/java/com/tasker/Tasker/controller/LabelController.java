package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.Label;
import com.tasker.Tasker.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<Label> createLabel(@RequestBody Label label) {
        Label createdLabel = labelService.createLabel(label.getName(), label.getColor());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLabel);
    }

    @GetMapping
    public ResponseEntity<List<Label>> getAllLabels() {
        List<Label> labels = labelService.getAllLabels();
        return ResponseEntity.ok(labels);
    }

    @GetMapping("/{labelId}")
    public ResponseEntity<Label> getLabel(@PathVariable Long labelId) {
        Label label = labelService.getLabel(labelId);
        return ResponseEntity.ok(label);
    }

    @PutMapping("/{labelId}")
    public ResponseEntity<Label> updateLabel(@PathVariable Long labelId, @RequestBody Label label) {
        Label updatedLabel = labelService.updateLabel(labelId, label.getName(), label.getColor());
        return ResponseEntity.ok(updatedLabel);
    }

    @DeleteMapping("/{labelId}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long labelId) {
        labelService.deleteLabel(labelId);
        return ResponseEntity.noContent().build();
    }
}