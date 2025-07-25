package com.example.tasksSystem.configurations;

import com.example.tasksSystem.Repositories.TaskRepository;
import com.example.tasksSystem.elements.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class TaskConfig {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskConfig(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

}
