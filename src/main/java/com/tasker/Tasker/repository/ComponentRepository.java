package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}