package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Project;
import com.tasker.Tasker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
}