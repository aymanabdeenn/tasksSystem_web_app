package com.example.tasksSystem.exceptions;

public class TaskNotFoundForUserException extends RuntimeException {
    public TaskNotFoundForUserException(String message) {
        super(message);
    }
}
