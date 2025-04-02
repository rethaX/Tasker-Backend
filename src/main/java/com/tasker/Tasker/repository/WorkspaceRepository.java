package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}