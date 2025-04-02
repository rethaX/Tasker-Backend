package com.tasker.Tasker.controller;

import com.tasker.Tasker.model.ListEntity;
import com.tasker.Tasker.service.ListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/lists")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> createList(@PathVariable Long projectId, @Valid @RequestBody ListEntity list) {
        ListEntity createdList = listService.createList(list.getName(), projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ListEntity>> getAllLists(@PathVariable Long projectId) {
        List<ListEntity> lists = listService.getAllLists(projectId);
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/{listId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> getList(@PathVariable Long listId) {
        ListEntity list = listService.getList(listId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{listId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> updateList(@PathVariable Long listId, @Valid @RequestBody ListEntity list) {
        ListEntity updatedList = listService.updateList(listId, list.getName());
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/{listId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
        listService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
}