package com.example.tasksSystem.elements;

import com.example.tasksSystem.exceptions.TaskNotFoundForUserException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @SequenceGenerator(
            name="user-sequence",
            sequenceName = "user-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user-sequence"
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "assigned_manager_id")
    private Manager assignedUserManager;

    @OneToMany(mappedBy = "assignedUser" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<Task>();

    @ManyToOne
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;

    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    public User() {}

    public User(String name , String username) {
        this.name = name;
        this.username = username;
    }

    public User(String name, Manager assignedUserManager) {
        this.name = name;
        this.assignedUserManager = assignedUserManager;
    }

    public User(String name, String username ,Manager assignedUserManager) {
        this.name = name;
        this.username = username;
        this.assignedUserManager = assignedUserManager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getAssignedUserManager() {
        return assignedUserManager;
    }

    public void setAssignedUserManager(Manager assignedUserManager) {
        this.assignedUserManager = assignedUserManager;
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(Team assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Task> getTasks(){
        return this.tasks;
    }

    public List<Task> getPersonalTasks(){
        List<Task> personalTasks = new ArrayList<Task>();
        for(Task task : this.tasks){
            if(task.getType().equals("personal")) personalTasks.add(task);
        }
        return personalTasks;
    }

    public List<Task> getJobTasks(){
        List<Task> jobTasks = new ArrayList<Task>();
        for(Task task: this.tasks){
            if(task.getType().equals("job")) jobTasks.add(task);
        }
        return jobTasks;
    }

    public Task isTaskInList(Long taskId){
        for(Task task : this.tasks){
            if(task.getId() == taskId) return task;
        }
        throw new TaskNotFoundForUserException("The task wasn't found in the user's tasks list." );
    }

    public void addTask(Task task){
        task.setAssignedUser(this);
        this.tasks.add(task);
    }

    public void removeTask(Long taskId){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setAssignedUser(null);
            this.tasks.remove(task);
        }
    }

    public Task updateTaskTitle(Long taskId , String title){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setTitle(title);
        }
        return task;
    }

    public Task updateTaskDescription(Long taskId , String description){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setDescription(description);
        }
        return task;
    }

    public Task updateTaskProgress(Long taskId , int progress){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setProgress(progress);
        }
        return task;
    }

    public Task updateTaskDueDate(Long taskId , LocalDate dueDate){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setDueDate(dueDate);
        }
        return task;
    }

    public Task updateTaskDueTime(Long taskId , LocalTime dueTime){
        Task task = isTaskInList(taskId);
        if(task != null){
            task.setDueTime(dueTime);
        }
        return task;
    }
}