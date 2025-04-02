package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
}