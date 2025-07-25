package com.example.tasksSystem.controllers;

import com.example.tasksSystem.Services.AccountService;
import com.example.tasksSystem.Services.ManagerService;
import com.example.tasksSystem.Services.RegistrationService;
import com.example.tasksSystem.Services.UserService;
import com.example.tasksSystem.dto.AuthLoginDTO;
import com.example.tasksSystem.dto.AuthSignUpDTO;
import com.example.tasksSystem.elements.*;
import com.example.tasksSystem.exceptions.UsernameAlreadyExistsException;
import com.example.tasksSystem.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestMapping("/shared")
public class SharedController {

    private final UserService userService;
    private final ManagerService managerService;
    private final AccountService accountService;
    private final RegistrationService registrationService;

    @Autowired
    public SharedController(UserService userService , ManagerService managerService , AccountService accountService , RegistrationService registrationService){
        this.userService = userService;
        this.managerService = managerService;
        this.accountService = accountService;
        this.registrationService = registrationService;
    }

    @GetMapping("/loginPage")
    public String main(Model model , @RequestParam(required = false) boolean error){
        model.addAttribute("auth" , new AuthLoginDTO());
        if(error) model.addAttribute("error" , "Incorrect username or password.");
        return "/authForms/login";
    }

    @GetMapping("/signUpUI")
    public String signUpUI(Model model){
        model.addAttribute("auth" , new AuthSignUpDTO());
        return "/authForms/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(Model model, @Valid @ModelAttribute("auth")AuthSignUpDTO authSignUpDTO , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/authForms/signUp";
        }

        Optional<Account> account = accountService.getAccountByUsername(authSignUpDTO.getUsername());
        if(!account.isPresent()){
            User user = new User(authSignUpDTO.getName() , authSignUpDTO.getUsername());
            registrationService.registerUser(user, authSignUpDTO.getUsername() , authSignUpDTO.getPassword());
            model.addAttribute("auth" , new AuthLoginDTO());
            return "/authForms/login";
        }
        else throw new UsernameAlreadyExistsException("The username " + authSignUpDTO.getUsername() + " is already taken.");
    }

    @PostMapping("/createNewTask")
    public String createNewTask(Model model , @RequestParam String taskName , @RequestParam String taskDescription , @RequestParam LocalDate taskDueDate, @RequestParam LocalTime taskDueTime, @RequestParam String type , @RequestParam(required = false) Long userId){
        LocalTime truncatedTime  = taskDueTime.truncatedTo(ChronoUnit.SECONDS);
        Task task = new Task(taskName , taskDescription , LocalDate.now() , taskDueDate , truncatedTime);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if(type.equals("personal")){
            Long loggedUserId = userDetails.getUser().getId();
            User user = userService.getUserByID(loggedUserId);
            task.setType("personal");
            userService.addTaskToUserWithObject(user , task);
            return "redirect:/user/userUI";
        }
        else{
            Long loggedManagerId = userDetails.getManager().getId();
            Manager manager = managerService.getManagerById(loggedManagerId);
            task.setType("job");
            userService.addTaskToUser(userId , task);
            model.addAttribute("manager" , manager);
            return "redirect:/manager/managerUI";
        }
    }

}