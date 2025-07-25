package com.example.tasksSystem.elements;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Role {

    @Id
    @SequenceGenerator(
            name = "role-sequence",
            sequenceName = "role-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role-sequence"
    )
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "roles")
    List<Account> accounts = new ArrayList<Account>();


    public Role(){}

    public Role(String name){
        this.name = name;
    }

    public Role(String name , List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
