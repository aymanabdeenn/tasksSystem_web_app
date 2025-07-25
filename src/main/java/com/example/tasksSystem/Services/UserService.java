package com.example.tasksSystem.Services;

import com.example.tasksSystem.Repositories.TaskRepository;
import com.example.tasksSystem.Repositories.UserRepository;
import com.example.tasksSystem.elements.Task;
import com.example.tasksSystem.elements.User;
import com.example.tasksSystem.exceptions.NoSuchUsernameExistsException;
import com.example.tasksSystem.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository , TaskRepository taskRepository){
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public User getUserByID(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("A user with the id " + userId + "does not exist"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchUsernameExistsException("A user with the username" + username + "does not exist"));
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public void addTaskToUser(Long id , Task task){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        user.addTask(task);
        userRepository.save(user);
    }

    public void addTaskToUserWithObject(User user , Task task){
        user.addTask(task);
        userRepository.save(user);
    }

    public void removeTaskFromUser(Long id, Long taskId){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        user.removeTask(taskId);
        userRepository.save(user);
    }

    public void removeTaskFromUserWithObject(User user, Long taskId){
        user.removeTask(taskId);
        userRepository.save(user);
    }

    @Transactional
    public void updateTaskProgress(Long id, Long taskId , int progress){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        Task task = user.updateTaskProgress(taskId , progress);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskDescription(Long id, Long taskId , String description){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        Task task = user.updateTaskDescription(taskId , description);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskTitle(Long id, Long taskId , String title){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        Task task = user.updateTaskTitle(taskId , title);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskDueDate(Long id, Long taskId , LocalDate dueDate){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        Task task = user.updateTaskDueDate(taskId , dueDate);
        taskRepository.save(task);
    }

    public void updateTaskDueTime(Long id, Long taskId , LocalTime dueTime){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with the ID" + id + "can not be found."));
        Task task = user.updateTaskDueTime(taskId , dueTime);
        taskRepository.save(task);
    }
    ////////////////////////////////////
    @Transactional
    public void updateTaskProgressWithObjects(User user, Long taskId , int progress){
        Task task = user.updateTaskProgress(taskId , progress);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskDescriptionWithObjects(User user, Long taskId , String description){
        Task task = user.updateTaskDescription(taskId , description);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskTitleWithObjects(User user, Long taskId , String title){
        Task task = user.updateTaskTitle(taskId , title);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskDueDateWithObjects(User user, Long taskId , LocalDate dueDate){
        Task task = user.updateTaskDueDate(taskId , dueDate);
        taskRepository.save(task);
    }

    public void updateTaskDueTimeWithObjects(User user, Long taskId, LocalTime dueTime) {
        Task task = user.updateTaskDueTime(taskId, dueTime);
        taskRepository.save(task);
    }
}