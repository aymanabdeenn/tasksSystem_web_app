package com.example.tasksSystem.security;

import com.example.tasksSystem.elements.Account;
import com.example.tasksSystem.elements.Manager;
import com.example.tasksSystem.elements.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Account account;

    public CustomUserDetails(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return this.account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
    }

    public User getUser(){
        return this.account.getUser();
    }

    public Manager getManager(){
        return this.account.getManager();
    }
}
