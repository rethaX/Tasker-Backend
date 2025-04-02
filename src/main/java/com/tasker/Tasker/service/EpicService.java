package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Epic;
import com.tasker.Tasker.model.Project;
import com.tasker.Tasker.repository.EpicRepository;
import com.tasker.Tasker.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpicService {

    private final EpicRepository epicRepository;
    private final ProjectRepository projectRepository;

    public EpicService(EpicRepository epicRepository, ProjectRepository projectRepository) {
        this.epicRepository = epicRepository;
        this.projectRepository = projectRepository;
    }

    public Epic createEpic(String name, String description, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        epic.setProject(project);
        return epicRepository.save(epic);
    }

    public Epic getEpic(Long epicId) {
        return epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found with id: " + epicId));
    }

    public List<Epic> getEpicsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return epicRepository.findAll().stream()
                .filter(epic -> epic.getProject().equals(project))
                .toList();
    }

    public List<Epic> getAllEpics() {
        return epicRepository.findAll();
    }

    public Epic updateEpic(Long epicId, String name, String description) {
        Epic epic = getEpic(epicId);
        epic.setName(name);
        epic.setDescription(description);
        return epicRepository.save(epic);
    }

    public void deleteEpic(Long epicId) {
        epicRepository.deleteById(epicId);
    }
}