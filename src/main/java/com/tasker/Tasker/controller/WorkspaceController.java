package com.tasker.Tasker.controller;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Workspace;
import com.tasker.Tasker.service.UserService;
import com.tasker.Tasker.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserService userService;

    public WorkspaceController(WorkspaceService workspaceService, UserService userService) {
        this.workspaceService = workspaceService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Workspace> createWorkspace(@Valid @RequestBody Workspace workspace) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username);
        Workspace createdWorkspace = workspaceService.createWorkspace(workspace.getName(), user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspace);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @GetMapping("/{workspaceId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Workspace> getWorkspace(@PathVariable Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspace(workspaceId);
        return ResponseEntity.ok(workspace);
    }

    @PutMapping("/{workspaceId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable Long workspaceId, @Valid @RequestBody Workspace workspace) {
        Workspace updatedWorkspace = workspaceService.updateWorkspace(workspaceId, workspace.getName());
        return ResponseEntity.ok(updatedWorkspace);
    }

    @DeleteMapping("/{workspaceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{workspaceId}/members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Workspace> addMember(@PathVariable Long workspaceId, @RequestParam Long userId) {
        Workspace updatedWorkspace = workspaceService.addMember(workspaceId, userId);
        return ResponseEntity.ok(updatedWorkspace);
    }

    @DeleteMapping("/{workspaceId}/members/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Workspace> removeMember(@PathVariable Long workspaceId, @PathVariable Long userId) {
        Workspace updatedWorkspace = workspaceService.removeMember(workspaceId, userId);
        return ResponseEntity.ok(updatedWorkspace);
    }
}