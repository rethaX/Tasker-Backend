package com.tasker.Tasker.repository;

import com.tasker.Tasker.model.Attachment;
import com.tasker.Tasker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTask(Task task);
}