package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Checklist;
import com.tasker.Tasker.model.Task;
import com.tasker.Tasker.repository.ChecklistRepository;
import com.tasker.Tasker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final TaskRepository taskRepository;

    public ChecklistService(ChecklistRepository checklistRepository, TaskRepository taskRepository) {
        this.checklistRepository = checklistRepository;
        this.taskRepository = taskRepository;
    }

    public Checklist createChecklist(String title, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        Checklist checklist = new Checklist();
        checklist.setTitle(title);
        checklist.setTask(task);
        return checklistRepository.save(checklist);
    }

    public Checklist getChecklist(Long checklistId) {
        return checklistRepository.findById(checklistId)
                .orElseThrow(() -> new RuntimeException("Checklist not found with id: " + checklistId));
    }

    public List<Checklist> getChecklistsByTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return checklistRepository.findAll().stream()
                .filter(checklist -> checklist.getTask().equals(task))
                .toList();
    }

    public List<Checklist> getAllChecklists() {
        return checklistRepository.findAll();
    }

    public Checklist updateChecklist(Long checklistId, String title) {
        Checklist checklist = getChecklist(checklistId);
        checklist.setTitle(title);
        return checklistRepository.save(checklist);
    }

    public void deleteChecklist(Long checklistId) {
        checklistRepository.deleteById(checklistId);
    }
}