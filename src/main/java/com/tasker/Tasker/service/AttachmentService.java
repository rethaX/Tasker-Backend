package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Attachment;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.repository.AttachmentRepository;
import com.tasker.Tasker.repository.TaskRepository;
import com.tasker.Tasker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Attachment uploadAttachment(Long taskId, Long userId, MultipartFile file) throws IOException {
        logger.info("Uploading attachment for task: {} by user: {}", taskId, userId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setFileUrl(filePath.toString());
        attachment.setTask(task);
        attachment.setUser(user);
        return attachmentRepository.save(attachment);
    }

    public java.util.List<Attachment> getAttachmentsByTask(Long taskId) {
        logger.info("Fetching attachments for task: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return attachmentRepository.findByTask(task);
    }

    public Attachment getAttachment(Long attachmentId) {
        logger.info("Fetching attachment: {}", attachmentId);
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + attachmentId));
    }

    public void deleteAttachment(Long attachmentId) {
        logger.info("Deleting attachment: {}", attachmentId);
        Attachment attachment = getAttachment(attachmentId);
        try {
            Files.deleteIfExists(Paths.get(attachment.getFileUrl()));
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", attachment.getFileUrl(), e);
        }
        attachmentRepository.deleteById(attachmentId);
    }
}