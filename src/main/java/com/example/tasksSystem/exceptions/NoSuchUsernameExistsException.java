package com.example.tasksSystem.exceptions;

public class NoSuchUsernameExistsException extends RuntimeException {
    public NoSuchUsernameExistsException(String message) {
        super(message);
    }
}
