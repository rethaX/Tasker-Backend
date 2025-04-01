package com.tasker.Tasker.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private Date dueDate;
}