package com.example.tasksSystem.Repositories;

import com.example.tasksSystem.elements.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
            "JOIN t.assignedUser u " +
            "JOIN u.assignedUserManager m " +
            "WHERE m.id = :managerId AND t.type = 'job'")
    List<Task> findTasksByManagerId(@Param("managerId") Long managerId);
}
