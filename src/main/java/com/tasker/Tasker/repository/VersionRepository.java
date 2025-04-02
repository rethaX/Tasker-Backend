package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {
}