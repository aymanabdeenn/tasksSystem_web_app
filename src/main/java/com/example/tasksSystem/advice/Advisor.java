package com.example.tasksSystem.advice;

import com.example.tasksSystem.dto.AuthLoginDTO;
import com.example.tasksSystem.dto.AuthSignUpDTO;
import com.example.tasksSystem.elements.User;
import com.example.tasksSystem.exceptions.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advisor {

    @ExceptionHandler(UserNotFoundException.class)
    public void userNotFound(UserNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundForUserException.class)
    public void taskNotForUser(TaskNotFoundForUserException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(TeamNotFoundForManagerException.class)
    public void teamNotFoundForManager(TeamNotFoundForManagerException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public void taskNotFound(TaskNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public void managerNotFound(ManagerNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(NoSuchUsernameExistsException.class)
    public String wrongUsername(Model model){
        model.addAttribute("message" , "Username doesn't exist.");
        model.addAttribute("auth" , new AuthLoginDTO());
        return "/authForms/login";
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String usernameExists(Model model){
        model.addAttribute("message" , "Username is taken.");
        model.addAttribute("auth" , new AuthSignUpDTO());
        return "/authForms/signUp";
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public String wrongCredentials(Model model){
        model.addAttribute("message" , "Incorrect username or password.");
        model.addAttribute("auth" , new AuthLoginDTO());
        return "/authForms/login";
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public void roleNotFound(RoleNotFoundException ex){
        System.out.println("Role not found");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String usernameNotFound(Model model){
        model.addAttribute("message" , "Username doesn't exist.");
        return "/authForms/login";
    }

}