package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
}