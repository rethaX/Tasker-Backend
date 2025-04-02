package com.tasker.Tasker.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TimeLogRequest {

    @NotNull(message = "Hours cannot be null")
    @Min(value = 1, message = "Hours must be at least 1")
    private Integer hours;

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}