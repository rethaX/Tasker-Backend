package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, Long> {
}