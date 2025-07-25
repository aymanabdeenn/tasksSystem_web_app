package com.example.tasksSystem.controllers;

import com.example.tasksSystem.Services.TaskService;
import com.example.tasksSystem.Services.UserService;
import com.example.tasksSystem.elements.Task;
import com.example.tasksSystem.elements.User;
import com.example.tasksSystem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService , TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/userUI")
    public String managerUI(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user" , user);
        return "/user/userGUI";
    }

    @GetMapping("/createTask")
    public String createTask(Model model){
        model.addAttribute("type" , "personal");
        return "/shared/createATask";
    }

    @GetMapping("/tasksSelection")
    public String tasksSelectionUI(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isTeamMember = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_TEAM_MEMBER"));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        User user = userService.getUserByID(userId);
        if(!isTeamMember){
            model.addAttribute("today" , LocalDate.now());
            model.addAttribute("tasks" , user.getPersonalTasks());
            return "/user/personalTasks";
        }
        return "/teamMember/tasksSelection";
    }

    @GetMapping("/showTasks")
    public String showTasks(Model model , @RequestParam String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        User user = userService.getUserByID(userId);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("todayDateTime" , LocalDateTime.now());
        if(action.equals("personal")){
            model.addAttribute("tasks" , user.getPersonalTasks());
            return "/user/personalTasks";
        }
        else {
            model.addAttribute("tasks" , user.getJobTasks());
            return "/user/workTasks";
        }
    }

    @PostMapping("/modTask")
    public String modTask(Model model , @RequestParam Long taskId , @RequestParam String action){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        User user = userService.getUserByID(userId);
        Task task = user.isTaskInList(taskId);
        if(action.equals("remove")){
            userService.removeTaskFromUserWithObject(user ,taskId);
            return "redirect:/user/showTasks?action=personal";
        }
        else {
            model.addAttribute("taskType" , task.getType());
            model.addAttribute("task" , task);
            model.addAttribute("today" , LocalDate.now());
            return "/shared/updateTasks";
        }
    }

    @PostMapping("/updateTask")
    public String updateTask(Model model , @RequestParam Long taskId , @RequestParam String action , @RequestParam String newData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        User user = userService.getUserByID(userId);
        switch(action){
            case "changeTitle": userService.updateTaskTitleWithObjects(user , taskId , newData); break;
            case "changeDescription": userService.updateTaskDescriptionWithObjects(user , taskId , newData); break;
            case "changeDueDate": userService.updateTaskDueDateWithObjects(user , taskId , LocalDate.parse(newData)); break;
            case "changeDueTime": userService.updateTaskDueTimeWithObjects(user , taskId, LocalTime.parse(newData)); break;
            case "changeProgress": {
                try{
                    int parsedProgress = Integer.parseInt(newData);
                    if(parsedProgress > 100 || parsedProgress < 0){
                        Task task = taskService.getTaskByID(taskId);
                        model.addAttribute("taskType" , task.getType());
                        model.addAttribute("task" , task);
                        model.addAttribute("today" , LocalDate.now());
                        model.addAttribute("error" , "Enter a number between 0 and a 100.");
                        return "/shared/updateTasks";
                    }
                    userService.updateTaskProgressWithObjects(user , taskId , Integer.parseInt(newData));
                    break;
                }
                catch(NumberFormatException ex){
                    Task task = taskService.getTaskByID(taskId);
                    model.addAttribute("taskType" , task.getType());
                    model.addAttribute("task" , task);
                    model.addAttribute("today" , LocalDate.now());
                    model.addAttribute("error" , "Enter a number.");
                    return "/shared/updateTasks";
                }
            }
        }
        Task task = user.isTaskInList(taskId);
        model.addAttribute("today", LocalDate.now());
        if(task.getType().equals("personal")){
            model.addAttribute("tasks" , user.getPersonalTasks());
            return "/user/personalTasks";
        }
        model.addAttribute("tasks", user.getJobTasks());
        return "/user/workTasks";
    }

}