package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}