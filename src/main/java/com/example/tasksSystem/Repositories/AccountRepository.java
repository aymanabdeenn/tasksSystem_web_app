package com.example.tasksSystem.Repositories;


import com.example.tasksSystem.elements.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByUsername(String username);

    public Account findByUserId(Long userId);

    public Account findFirstByUsername(String username);
}
