package com.example.tasksSystem.Repositories;

import com.example.tasksSystem.elements.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
