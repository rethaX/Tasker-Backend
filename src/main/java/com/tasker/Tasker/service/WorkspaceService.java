package com.tasker.Tasker.service;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Workspace;
import com.tasker.Tasker.repository.UserRepository;
import com.tasker.Tasker.repository.WorkspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WorkspaceService {
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceService.class);

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    public Workspace createWorkspace(String name, Long userId) {
        logger.info("Creating workspace: {} for user: {}", name, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.setMembers(Collections.singletonList(user));
        return workspaceRepository.save(workspace);
    }

    public List<Workspace> getAllWorkspaces() {
        logger.info("Fetching all workspaces");
        return workspaceRepository.findAll();
    }

    public Workspace getWorkspace(Long workspaceId) {
        logger.info("Fetching workspace: {}", workspaceId);
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found with id: " + workspaceId));
    }

    public Workspace updateWorkspace(Long workspaceId, String name) {
        logger.info("Updating workspace: {}", workspaceId);
        Workspace workspace = getWorkspace(workspaceId);
        workspace.setName(name);
        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(Long workspaceId) {
        logger.info("Deleting workspace: {}", workspaceId);
        workspaceRepository.deleteById(workspaceId);
    }

    public Workspace addMember(Long workspaceId, Long userId) {
        logger.info("Adding user: {} to workspace: {}", userId, workspaceId);
        Workspace workspace = getWorkspace(workspaceId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<User> members = new java.util.ArrayList<>(workspace.getMembers());
        if (!members.contains(user)) {
            members.add(user);
            workspace.setMembers(members);
        }
        return workspaceRepository.save(workspace);
    }

    public Workspace removeMember(Long workspaceId, Long userId) {
        logger.info("Removing user: {} from workspace: {}", userId, workspaceId);
        Workspace workspace = getWorkspace(workspaceId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<User> members = new java.util.ArrayList<>(workspace.getMembers());
        members.remove(user);
        workspace.setMembers(members);
        return workspaceRepository.save(workspace);
    }
}