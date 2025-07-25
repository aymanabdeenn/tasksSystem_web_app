package com.example.tasksSystem.Repositories;

import com.example.tasksSystem.elements.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    public Optional<Manager> findByUsername(String username);
}
