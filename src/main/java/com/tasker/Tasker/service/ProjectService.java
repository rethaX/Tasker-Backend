package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Project;
import com.tasker.Tasker.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name, String description, User user) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUser(user);
        return projectRepository.save(project);
    }

    public List<Project> getUserProjects(User user) {
        return projectRepository.findByUser(user);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
    }

    public Project updateProject(Long projectId, String name, String description) {
        Project project = getProject(projectId);
        project.setName(name);
        project.setDescription(description);
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}