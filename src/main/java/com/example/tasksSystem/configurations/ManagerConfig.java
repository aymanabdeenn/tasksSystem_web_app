package com.example.tasksSystem.configurations;

import com.example.tasksSystem.Repositories.ManagerRepository;
import com.example.tasksSystem.Services.RegistrationService;
import com.example.tasksSystem.elements.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ManagerConfig {

    private final ManagerRepository managerRepository;
    private final RegistrationService registrationService;

    @Autowired
    public ManagerConfig(ManagerRepository managerRepository , RegistrationService registrationService){
        this.managerRepository = managerRepository;
        this.registrationService = registrationService;
    }

    @Bean
    CommandLineRunner commandLineRunnerManager(){
        return args -> {
            Manager manager1 = new Manager("Manager1" , "man1@gmail.com");
            Manager manager2 = new Manager("Manager2" , "man2@gmail.com");
            registrationService.registerManager(manager1 , "man1@gmail.com" , "123");
            registrationService.registerManager(manager2 , "man2@gmail.com" , "123");
        };
    }
}
