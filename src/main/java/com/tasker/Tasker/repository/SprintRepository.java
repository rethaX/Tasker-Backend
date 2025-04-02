package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}