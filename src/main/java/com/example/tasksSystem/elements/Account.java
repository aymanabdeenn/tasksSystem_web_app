package com.example.tasksSystem.elements;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Account {

    @Id
    @SequenceGenerator(
            name = "authentication-sequence",
            sequenceName = "authentication-name",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authentication-sequence"
    )
    Long id;

    @Column(
            unique = true,
            nullable = false
    )
    String username;

    @Column(
            nullable = false
    )
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id" , referencedColumnName = "id")}
    )
    List<Role> roles = new ArrayList<Role>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Account(){}

    public Account(String username , String password){
        this.username = username;
        this.password = password;
    }

    public Account(String username , User user){
        this.username = username;
        this.user = user;
    }

    public Account(String username , Manager manager){
        this.username = username;
        this.manager = manager;
    }

    public Account(String username , String password , User user){
        this.username = username;
        this.password = password;
        this.user = user;
    }

    public Account(String username , String password, Manager manager){
        this.username = username;
        this.password = password;
        this.manager = manager;
    }

    public Account(String username, String password , Role role) {
        this.username = username;
        this.password = password;
        this.roles.add(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRoles(Role role){
        this.roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
