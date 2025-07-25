package com.example.tasksSystem.controllers;

import com.example.tasksSystem.Services.ManagerService;
import com.example.tasksSystem.Services.TaskService;
import com.example.tasksSystem.Services.UserService;
import com.example.tasksSystem.elements.Manager;
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
import java.time.LocalTime;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public ManagerController(ManagerService managerService , UserService userService , TaskService taskService){
        this.managerService = managerService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/managerUI")
    public String managerUI(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Manager manager = userDetails.getManager();
        model.addAttribute("manager" , manager);
        return "/manager/managerGUI";
    }

    @GetMapping("/createTeam")
    public String createTeam(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        model.addAttribute("manager" , manager);
        return "/manager/createNewTeam";
    }

    @GetMapping("/assignUserToTeamUI")
    public String assignUserToTeamUI(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        model.addAttribute("teams" , manager.getAllTeams());
        model.addAttribute("users" , managerService.getAvailableUsers());
        return "/manager/assignUserToTeam";
    }

    @PostMapping("/assignUserToTeam")
    public String assignUserToTeam(Model model, @RequestParam Long teamId , @RequestParam Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        managerService.assignUserToTeam(teamId , userId);
        return "redirect:/manager/managerUI";
    }

    @GetMapping("/viewTeams")
    public String viewTeams(Model model, @RequestParam(required = false) Long teamId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        if(teamId != null){
            model.addAttribute("users" , manager.getAllUsersForATeam(teamId));
        }
        model.addAttribute("teams" , manager.getAllTeams());
        return "/manager/showTeams";
    }


    @GetMapping("/createTask")
    public String createTask(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        model.addAttribute("type" , "job");
        model.addAttribute("users" , managerService.getAllUsersForManager(manager));
        return "/shared/createATask";
    }

    @GetMapping("/viewReport")
    public String viewReport(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        model.addAttribute("tasks" , taskService.getTasksUnderManager(managerId));
        model.addAttribute("today" , LocalDate.now());
        return "/manager/showTasksReport";
    }

    @PostMapping("/createNewTeam")
    public String createNewTeam(Model model , @RequestParam String teamName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getManager().getId();
        Manager manager = managerService.getManagerById(managerId);
        if(teamName.equals("")){
            model.addAttribute("error" , "Enter a name.");
            model.addAttribute("manager" , manager);
            return "/manager/createNewTeam";
        }
        managerService.createTeamWithObject(manager , teamName);
        return "redirect:/manager/managerUI";
    }

    @PostMapping("/modTask")
    public String modTask(Model model , @RequestParam Long taskId , @RequestParam Long assigneeId, @RequestParam String action , @RequestParam(required = false) String role){
        User user = userService.getUserByID(assigneeId);
        Task task = user.isTaskInList(taskId);
        if(action.equals("remove")){
            userService.removeTaskFromUserWithObject(user ,taskId);
            return "redirect:/manager/viewReport";
        }
        else {
            model.addAttribute("taskType" , task.getType());
            model.addAttribute("task" , task);
            model.addAttribute("today" , LocalDate.now());
            model.addAttribute("role" , "manager");
            return "/shared/updateTasks";
        }
    }

    @PostMapping("/updateTask")
    public String updateTask(Model model , @RequestParam Long taskId , @RequestParam(name = "assigneeId") Long assigneeId,  @RequestParam String action , @RequestParam String newData){
        User user = userService.getUserByID(assigneeId);
        switch(action){
            case "changeTitle": userService.updateTaskTitleWithObjects(user , taskId , newData); break;
            case "changeDescription": userService.updateTaskDescriptionWithObjects(user , taskId , newData); break;
            case "changeDueDate": userService.updateTaskDueDateWithObjects(user , taskId , LocalDate.parse(newData)); break;
            case "changeDueTime": userService.updateTaskDueTimeWithObjects(user , taskId, LocalTime.parse(newData)); break;
            case "changeProgress": {
                try{
                    int parsedProgress = Integer.parseInt(newData);
                    if(parsedProgress > 100 || parsedProgress < 0) {
                        Task task = taskService.getTaskByID(taskId);
                        model.addAttribute("taskType" , task.getType());
                        model.addAttribute("task" , task);
                        model.addAttribute("today" , LocalDate.now());
                        model.addAttribute("role" , "manager");
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
                    model.addAttribute("role" , "manager");
                    model.addAttribute("error" , "Enter a number.");
                    return "/shared/updateTasks";
                }
            }
        }
         return "redirect:/manager/viewReport";
    }

}