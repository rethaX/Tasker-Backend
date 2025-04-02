package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskAutomationService {
    private static final Logger logger = LoggerFactory.getLogger(TaskAutomationService.class);

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    public TaskAutomationService(TaskRepository taskRepository, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void checkDueTasks() {
        logger.info("Checking due tasks");
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now().plusDays(1))) {
                emailService.sendTaskNotification(task.getAssignee().getEmail(),
                        "Task Due Soon: " + task.getTitle());
            }
        }
    }
}