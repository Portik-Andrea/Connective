package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.repositories.ITaskRepository;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.TaskNotFoundException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService implements ITaskService {

    private ITaskRepository taskRepository;

    private IUserService userService;

    public TaskService(ITaskRepository taskRepository, UserService userService){
        this.taskRepository = taskRepository;
        this.userService = userService;
    }
    @Override
    public List<Task> findAllByAssigneeUser(Long userId) {
        if(userService.findUserByID(userId)!= null){
            List<Task> userTasks = taskRepository.findAllByAssignedToUser(userId);
            return userTasks;
        }
        else{
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        } else{
            throw new TaskNotFoundException(id);
        }
    }
}
