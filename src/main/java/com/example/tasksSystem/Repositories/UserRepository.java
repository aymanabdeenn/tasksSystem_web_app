package com.example.tasksSystem.Repositories;

import com.example.tasksSystem.elements.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.assignedTeam IS NULL")
    List<User> findUsersWithoutTeam();

    public Optional<User> findByUsername(String username);

}
