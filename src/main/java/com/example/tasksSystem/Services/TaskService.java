package com.example.tasksSystem.Services;

import com.example.tasksSystem.Repositories.TaskRepository;
import com.example.tasksSystem.elements.Task;
import com.example.tasksSystem.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task getTaskByID(Long taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("The task with the id" + taskId + "does not exist."));
    }

    public List<Task> getTasksUnderManager(Long managerId){
        return taskRepository.findTasksByManagerId(managerId);
    }

}
