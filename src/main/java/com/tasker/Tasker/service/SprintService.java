package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Project;
import com.tasker.Tasker.model.Sprint;
import com.tasker.Tasker.repository.ProjectRepository;
import com.tasker.Tasker.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SprintService {
    private static final Logger logger = LoggerFactory.getLogger(SprintService.class);

    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    public SprintService(SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }

    public Sprint createSprint(String name, LocalDateTime startDate, LocalDateTime endDate, Long projectId) {
        logger.info("Creating sprint: {} for project: {}", name, projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        Sprint sprint = new Sprint();
        sprint.setName(name);
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
        sprint.setProject(project);
        return sprintRepository.save(sprint);
    }

    public Sprint getSprint(Long sprintId) {
        logger.info("Fetching sprint: {}", sprintId);
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found with id: " + sprintId));
    }

    public java.util.List<Sprint> getAllSprints() {
        logger.info("Fetching all sprints");
        return sprintRepository.findAll();
    }

    public Sprint updateSprint(Long sprintId, String name, LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Updating sprint: {}", sprintId);
        Sprint sprint = getSprint(sprintId);
        sprint.setName(name);
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
        return sprintRepository.save(sprint);
    }

    public void deleteSprint(Long sprintId) {
        logger.info("Deleting sprint: {}", sprintId);
        sprintRepository.deleteById(sprintId);
    }

    public Sprint startSprint(Long sprintId) {
        logger.info("Starting sprint: {}", sprintId);
        Sprint sprint = getSprint(sprintId);
        sprint.setStatus("Active");
        return sprintRepository.save(sprint);
    }

    public Sprint completeSprint(Long sprintId) {
        logger.info("Completing sprint: {}", sprintId);
        Sprint sprint = getSprint(sprintId);
        sprint.setStatus("Completed");
        return sprintRepository.save(sprint);
    }
}