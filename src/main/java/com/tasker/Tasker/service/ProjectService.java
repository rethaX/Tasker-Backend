package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Project;
import com.tasker.Tasker.model.User;
import com.tasker.Tasker.repository.ProjectRepository;
import com.tasker.Tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public Project createProject(Project project) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        project.setUser(user);
        return projectRepository.save(project);
    }

    public List<Project> getUserProjects() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return projectRepository.findByUserId(user.getId());
    }
}