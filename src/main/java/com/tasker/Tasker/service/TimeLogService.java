package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.model.TimeLog;
import com.tasker.Tasker.repository.TaskRepository;
import com.tasker.Tasker.repository.TimeLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final TaskRepository taskRepository;

    public TimeLogService(TimeLogRepository timeLogRepository, TaskRepository taskRepository) {
        this.timeLogRepository = timeLogRepository;
        this.taskRepository = taskRepository;
    }

    public TimeLog logTime(Long taskId, int hours, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        TimeLog timeLog = new TimeLog();
        timeLog.setTask(task);
        timeLog.setHours(hours);
        timeLog.setUser(user);
        timeLog.setLoggedAt(LocalDateTime.now());
        return timeLogRepository.save(timeLog);
    }

    public List<TimeLog> getAllTimeLogs() {
        return timeLogRepository.findAll();
    }

    public TimeLog getTimeLog(Long timeLogId) {
        return timeLogRepository.findById(timeLogId)
                .orElseThrow(() -> new RuntimeException("TimeLog not found with id: " + timeLogId));
    }

    public TimeLog updateTimeLog(Long timeLogId, int hours) {
        TimeLog timeLog = getTimeLog(timeLogId);
        timeLog.setHours(hours);
        return timeLogRepository.save(timeLog);
    }

    public void deleteTimeLog(Long timeLogId) {
        timeLogRepository.deleteById(timeLogId);
    }
}