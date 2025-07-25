package com.example.tasksSystem.configurations;

import com.example.tasksSystem.Repositories.UserRepository;
import com.example.tasksSystem.Services.RegistrationService;
import com.example.tasksSystem.elements.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    @Autowired
    public UserConfig(UserRepository userRepository , RegistrationService registrationService){
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @Bean
    CommandLineRunner commandLineRunnerUser(){
        return args -> {
            User user1 = new User("User1" , "user1@gmail.com");
            User user2 = new User("User2" , "user2@gmail.com");
            registrationService.registerUser(user1 , "user1@gmail.com" , "123");
            registrationService.registerUser(user2 , "user2@gmail.com" , "123");
        };
    }
}
