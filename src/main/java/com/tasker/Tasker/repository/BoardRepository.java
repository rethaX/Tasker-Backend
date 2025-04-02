package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}