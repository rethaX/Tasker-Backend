package com.tasker.Tasker.controller;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Attachment;
import com.tasker.Tasker.service.AttachmentService;
import com.tasker.Tasker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final UserService userService;

    public AttachmentController(AttachmentService attachmentService, UserService userService) {
        this.attachmentService = attachmentService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Attachment> uploadAttachment(@PathVariable Long taskId, @RequestParam("file") MultipartFile file) throws IOException {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username);
        Attachment attachment = attachmentService.uploadAttachment(taskId, user.getId(), file);
        return ResponseEntity.status(HttpStatus.CREATED).body(attachment);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Attachment>> getAttachmentsByTask(@PathVariable Long taskId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByTask(taskId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/{attachmentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachment(attachmentId);
        return ResponseEntity.ok(attachment);
    }

    @DeleteMapping("/{attachmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.noContent().build();
    }
}