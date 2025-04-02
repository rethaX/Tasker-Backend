package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
}