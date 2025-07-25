package com.example.tasksSystem.Services;

import com.example.tasksSystem.Repositories.AccountRepository;
import com.example.tasksSystem.Repositories.ManagerRepository;
import com.example.tasksSystem.Repositories.RoleRepository;
import com.example.tasksSystem.Repositories.UserRepository;
import com.example.tasksSystem.elements.Account;
import com.example.tasksSystem.elements.Role;
import com.example.tasksSystem.elements.User;
import com.example.tasksSystem.elements.Manager;
import com.example.tasksSystem.exceptions.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, ManagerRepository managerRepository , AccountRepository accountRepository , RoleRepository roleRepository , PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void registerUser(User user , String username , String password){
        String encodedPassword = passwordEncoder.encode(password);
        Account account = new Account(username , encodedPassword , user);
        Role userRole = roleRepository.findById(1L).orElseThrow(() -> new RoleNotFoundException("Role not found."));
        account.addRoles(userRole);
        accountRepository.save(account);
    }

    public void registerManager(Manager manager , String username , String password){
        String encodedPassword = passwordEncoder.encode(password);
        Account account = new Account(username , encodedPassword, manager);
        Role managerRole = roleRepository.findById(3L).orElseThrow(() -> new RoleNotFoundException("Role not found."));
        account.addRoles(managerRole);
        accountRepository.save(account);
    }

}