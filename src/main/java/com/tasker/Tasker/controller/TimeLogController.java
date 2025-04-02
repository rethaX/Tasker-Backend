package com.tasker.Tasker.controller;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.TimeLog;
import com.tasker.Tasker.model.TimeLogRequest;
import com.tasker.Tasker.service.TimeLogService;
import com.tasker.Tasker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/timelogs")
public class TimeLogController {

    private final TimeLogService timeLogService;
    private final UserService userService;

    public TimeLogController(TimeLogService timeLogService, UserService userService) {
        this.timeLogService = timeLogService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TimeLog> createTimeLog(
            @PathVariable Long taskId,
            @Valid @RequestBody TimeLogRequest timeLogRequest) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username);
        TimeLog timeLog = timeLogService.logTime(taskId, timeLogRequest.getHours(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeLog);
    }

    @GetMapping
    public ResponseEntity<List<TimeLog>> getAllTimeLogs() {
        List<TimeLog> timeLogs = timeLogService.getAllTimeLogs();
        return ResponseEntity.ok(timeLogs);
    }

    @GetMapping("/{timeLogId}")
    public ResponseEntity<TimeLog> getTimeLog(@PathVariable Long timeLogId) {
        TimeLog timeLog = timeLogService.getTimeLog(timeLogId);
        return ResponseEntity.ok(timeLog);
    }

    @PutMapping("/{timeLogId}")
    public ResponseEntity<TimeLog> updateTimeLog(
            @PathVariable Long timeLogId,
            @Valid @RequestBody TimeLogRequest timeLogRequest) {
        TimeLog updatedTimeLog = timeLogService.updateTimeLog(timeLogId, timeLogRequest.getHours());
        return ResponseEntity.ok(updatedTimeLog);
    }

    @DeleteMapping("/{timeLogId}")
    public ResponseEntity<Void> deleteTimeLog(@PathVariable Long timeLogId) {
        timeLogService.deleteTimeLog(timeLogId);
        return ResponseEntity.noContent().build();
    }
}