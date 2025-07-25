package com.example.tasksSystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskDTO {

    private String title;
    private LocalDate dueDate;
    private LocalTime dueTime;

    public TaskDTO(){}

    public TaskDTO(String title , LocalDate dueDate , LocalTime dueTime){
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalTime dueTime) {
        this.dueTime = dueTime;
    }
}
