package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}