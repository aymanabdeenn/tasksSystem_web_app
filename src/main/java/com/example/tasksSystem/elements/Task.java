package com.example.tasksSystem.elements;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table
public class Task {

    @Id
    @SequenceGenerator(
            name="task-sequence",
            sequenceName = "task-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task-sequence"
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String title;

    @Column(
            nullable = true
    )
    private String description;

    @Column(
            nullable = false
    )
    private LocalDate startDate;

    @Column(
            nullable = false
    )
    private LocalDate dueDate;

    @Column(
            nullable = false
    )
    private LocalTime dueTime;

    @Column(
            nullable = false
    )
    private int progress = 0;

    @ManyToOne
    @JoinColumn(name="assigned_user_id")
    private User assignedUser;

    @Column(
            nullable = false
    )
    private String type;

    public Task(){}

    public Task(String title, String description,LocalDate startDate , LocalDate dueDate , LocalTime dueTime) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public Task(String title, String description, LocalDate dueDate , String type) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDaysUntilDue(LocalDate now) {
        return (int)ChronoUnit.DAYS.between(now , dueDate);
    }

    public int getPercentageByTimePast(){
        int totalDays = getDaysUntilDue(this.startDate);
        if(totalDays <= 0) return 0;
        int pastDays = totalDays - getDaysUntilDue(LocalDate.now());
        return (int)(pastDays / totalDays) * 100;
    }

    public LocalDateTime getDueDateTime(){
        return LocalDateTime.of(this.dueDate , this.dueTime);
    }

    public long remainingPeriod(){
        long seconds = Duration.between(LocalDateTime.now(), getDueDateTime()).getSeconds();
        if (seconds <= 0)  return -1;
        return (seconds <= 86_400) ? 1 : 2;
    }
}