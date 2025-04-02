package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.*;
import com.tasker.Tasker.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ListRepository listRepository;
    private final EpicRepository epicRepository;
    private final SprintRepository sprintRepository;
    private final UserRepository userRepository;
    private final SlackIntegrationService slackIntegrationService;
    private final EmailService emailService;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, ListRepository listRepository,
                       EpicRepository epicRepository, SprintRepository sprintRepository, UserRepository userRepository,
                       SlackIntegrationService slackIntegrationService, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.listRepository = listRepository;
        this.epicRepository = epicRepository;
        this.sprintRepository = sprintRepository;
        this.userRepository = userRepository;
        this.slackIntegrationService = slackIntegrationService;
        this.emailService = emailService;
    }

    public Task createTask(String title, String description, Long projectId, Long listId, Long epicId, Long sprintId, Long assigneeId, LocalDateTime dueDate) {
        logger.info("Creating task: {} for project: {}", title, projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        ListEntity list = null;
        if (listId != null) {
            list = listRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("List not found with id: " + listId));
            if (!list.getBoard().getProject().getId().equals(projectId)) {
                throw new RuntimeException("List’s board does not belong to the specified project: " + projectId);
            }
        }
        Epic epic = epicId != null ? epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found with id: " + epicId)) : null;
        Sprint sprint = sprintId != null ? sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found with id: " + sprintId)) : null;
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + assigneeId));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setProject(project);
        task.setList(list);
        task.setEpic(epic);
        task.setSprint(sprint);
        task.setAssignee(assignee);
        task.setReporter(assignee);
        task.setDueDate(dueDate);
        Task savedTask = taskRepository.save(task);
        slackIntegrationService.sendSlackNotification("New task created: " + savedTask.getTitle());
        emailService.sendTaskNotification(assignee.getEmail(), savedTask.getTitle());
        return savedTask;
    }

    public List<Task> getTasksByProject(Long projectId) {
        logger.info("Fetching tasks for project: {}", projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return taskRepository.findByProject(project);
    }

    public Page<Task> getAllTasks(String status, String priority, Long assigneeId, Pageable pageable) {
        logger.info("Fetching all tasks with filters - status: {}, priority: {}, assigneeId: {}", status, priority, assigneeId);
        return taskRepository.findAllByFilters(status, priority, assigneeId, pageable);
    }

    public Task getTask(Long taskId) {
        logger.info("Fetching task: {}", taskId);
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }

    public Task updateTask(Long taskId, String title, String description, String status, String priority, LocalDateTime dueDate) {
        logger.info("Updating task: {}", taskId);
        Task task = getTask(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        logger.info("Deleting task: {}", taskId);
        taskRepository.deleteById(taskId);
    }

    public List<Task> createBulkTasks(List<TaskRequest> tasks) {
        logger.info("Creating bulk tasks: {}", tasks.size());
        return tasks.stream().map(taskRequest -> createTask(
                taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getProjectId(),
                taskRequest.getListId(), taskRequest.getEpicId(), taskRequest.getSprintId(),
                taskRequest.getAssigneeId(), taskRequest.getDueDate()
        )).collect(Collectors.toList());
    }

    public Task assignTask(Long taskId, Long userId) {
        logger.info("Assigning task: {} to user: {}", taskId, userId);
        Task task = getTask(taskId);
        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public List<Task> searchTasks(String keyword) {
        logger.info("Searching tasks with keyword: {}", keyword);
        return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
}