package com.tasker.Tasker.repository;

import com.tasker.Tasker.entity.User;
import com.tasker.Tasker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
}