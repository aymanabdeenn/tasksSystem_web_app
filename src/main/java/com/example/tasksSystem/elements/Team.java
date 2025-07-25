package com.example.tasksSystem.elements;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Team {

    @Id
    @SequenceGenerator(
            name = "team-sequence",
            sequenceName = "team-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "team-sequence"
    )
    Long id;

    @Column(
            nullable = false
    )
    String name;

    @ManyToOne
    @JoinColumn(name = "assigned_manager_id")
    Manager assignedTeamManager;

    @OneToMany(mappedBy = "assignedTeam" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<User>();

    public Team(){}

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, Manager assignedTeamManager) {
        this.name = name;
        this.assignedTeamManager = assignedTeamManager;
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

    public Manager getAssignedTeamManager() {
        return assignedTeamManager;
    }

    public void setAssignedTeamManager(Manager assignedTeamManager) {
        this.assignedTeamManager = assignedTeamManager;
    }

    public List<User> getUsers(){
        return this.users;
    }

    public void addUser(User user){
        user.setAssignedTeam(this);
        user.setAssignedUserManager(this.getAssignedTeamManager());
        this.users.add(user);
    }

}
