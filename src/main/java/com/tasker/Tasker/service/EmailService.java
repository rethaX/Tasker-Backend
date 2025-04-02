package com.tasker.Tasker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendTaskNotification(String email, String taskTitle) {
        // Placeholder for email sending logic (e.g., using JavaMailSender)
        System.out.println("Email sent to " + email + " for task: " + taskTitle);
    }
}