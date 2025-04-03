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
@RequestMapping("/api")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping("/boards/{boardId}/lists")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> createList(@PathVariable Long boardId, @Valid @RequestBody ListEntity listRequest) {
        ListEntity list = listService.createList(listRequest.getName(), boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/boards/{boardId}/lists")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ListEntity>> getAllLists(@PathVariable Long boardId) {
        List<ListEntity> lists = listService.getAllLists(boardId);
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/lists/{listId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> getList(@PathVariable Long listId) {
        ListEntity list = listService.getList(listId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/lists/{listId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListEntity> updateList(@PathVariable Long listId, @Valid @RequestBody ListEntity listRequest) {
        ListEntity updatedList = listService.updateList(listId, listRequest.getName());
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/lists/{listId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
        listService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
}