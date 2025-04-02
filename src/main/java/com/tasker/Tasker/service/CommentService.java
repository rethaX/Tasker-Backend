package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Comment;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.repository.CommentRepository;
import com.tasker.Tasker.repository.TaskRepository;
import com.tasker.Tasker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(String content, Long taskId, Long userId) {
        logger.info("Creating comment for task: {} by user: {}", taskId, userId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setTask(task);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public java.util.List<Comment> getCommentsByTask(Long taskId) {
        logger.info("Fetching comments for task: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return commentRepository.findByTask(task);
    }

    public Comment getComment(Long commentId) {
        logger.info("Fetching comment: {}", commentId);
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }

    public Comment updateComment(Long commentId, String content) {
        logger.info("Updating comment: {} with content: {}", commentId, content);
        Comment comment = getComment(commentId);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        logger.info("Deleting comment: {}", commentId);
        commentRepository.deleteById(commentId);
    }
}