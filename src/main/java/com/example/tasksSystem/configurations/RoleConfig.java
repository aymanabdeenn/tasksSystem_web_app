package com.example.tasksSystem.configurations;

import com.example.tasksSystem.Repositories.RoleRepository;
import com.example.tasksSystem.elements.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;


@Configuration
public class RoleConfig {

    private final RoleRepository roleRepository;

    public RoleConfig(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Bean
    @Order(1)
    public CommandLineRunner commandLineRunner(){
        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role teamMemberRole = new Role("ROLE_TEAM_MEMBER");
            Role managerRole = new Role("ROLE_MANAGER");

            roleRepository.saveAll(List.of(userRole , teamMemberRole , managerRole));
        };
    }
}
