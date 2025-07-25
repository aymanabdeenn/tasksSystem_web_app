package com.example.tasksSystem.Services;

import com.example.tasksSystem.Repositories.*;
import com.example.tasksSystem.elements.*;
import com.example.tasksSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository , TaskRepository taskRepository , UserRepository userRepository , TeamRepository teamRepository , AccountRepository accountRepository , RoleRepository roleRepository){
        this.managerRepository = managerRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    public Manager getManagerById(Long managerId){
        return managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFoundException("The manager with the id " + managerId + "does not exist."));
    }

    public Manager getManagerByUsername(String username){
        return managerRepository.findByUsername(username).orElseThrow(() -> new NoSuchUsernameExistsException("A manager with the username" + username + "does not exist"));
    }

    public List<User> getAvailableUsers(){
        return userRepository.findUsersWithoutTeam();
    }

    public List<User> getAllUsersForManager(Manager manager){
        List<User> users = new ArrayList<User>();
        for(Team team : manager.getAllTeams()){
            for(User user : team.getUsers()) users.add(user);
        }
        return users;
    }

    public User searchInManagersUsers(Manager manager , Long userId){
        for(Team team : manager.getAllTeams()){
            for(User user : team.getUsers()){
                if(user.getId() == userId) return user;
            }
        }
        throw new UserNotFoundException("The user with the id " + userId + "does not exist for this manager.");
    }

    public void createTeam(Long managerId , String name){
        Manager manager = getManagerById(managerId);
        Team team = new Team(name);
        manager.addTeamToList(team);
        managerRepository.save(manager);
    }

    public void createTeamWithObject(Manager manager , String name){
        Team team = new Team(name);
        manager.addTeamToList(team);
        managerRepository.save(manager);
    }

    public void assignUserToTeam(Long teamId , Long userId){
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundForManagerException("The team with the Id " + teamId + "does not exist."));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("The user with the Id " + userId + "does not exist."));
        Account account = accountRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(2L).orElseThrow(() -> new RoleNotFoundException("The team-member role doesn't exist."));
        account.addRoles(role);
        accountRepository.save(account);
        team.addUser(user);
        teamRepository.save(team);
    }

    public void assignTaskToTeamMember(Long managerId, Long userId, Long taskId){
        Manager manager = getManagerById(managerId);
        User user = searchInManagersUsers(manager , userId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("The task with the id " + taskId + "does not exist"));
        user.addTask(task);
        userRepository.save(user);
    }

    public void createATask(Task task){
        taskRepository.save(task);
    }

}