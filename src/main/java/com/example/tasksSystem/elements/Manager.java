package com.example.tasksSystem.elements;

import com.example.tasksSystem.exceptions.TeamNotFoundForManagerException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Manager {

    @Id
    @SequenceGenerator(
            name = "manager-sequence",
            sequenceName = "manager-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "manager-sequence"
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @OneToMany(mappedBy = "assignedTeamManager" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<Team>();

    public Manager() {}

    public Manager(String name , String username) {
        this.name = name;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getAllUsersForATeam(Long teamId){
          for(Team team : this.teams){
              if(team.getId() == teamId) return team.getUsers();
          }
          throw new TeamNotFoundForManagerException("The team with the id " + teamId + "does not belong to this manager.");
    }

    public List<Team> getAllTeams(){
        return this.teams;
    }

    public Team getCertainTeam(Long teamId){
        for(Team team : this.teams){
            if(team.getId() == teamId){
                return team;
            }
        }
        throw new TeamNotFoundForManagerException("The team with the id " + teamId + " is not assigned for you.");
    }


    public void addTeamToList(Team team){
        team.setAssignedTeamManager(this);
        this.teams.add(team);
    }

}