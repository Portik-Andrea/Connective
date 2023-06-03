package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.controllers.dto.UpdateTaskDTO;
import ro.sapientia.Backend.domains.Priority;
import ro.sapientia.Backend.domains.Status;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.repositories.ITaskRepository;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.TaskNotFoundException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.Date;
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

    @Override
    @Transactional(readOnly = false)
    public boolean updateTask(UpdateTaskDTO updateTaskDTO) {
        Optional<Task> task = taskRepository.findById(updateTaskDTO.getTaskId());
        if(task.isPresent()){
            UserEntity oldAssignedUser = task.get().deleteAssignedUser();
            taskRepository.save(task.get());
            userService.refreshUser(oldAssignedUser);
        }else{
            throw new TaskNotFoundException(updateTaskDTO.getTaskId());
        }
        task = taskRepository.findById(updateTaskDTO.getTaskId());
        if(task.isPresent()){
            UserEntity user = userService.findUserByID(updateTaskDTO.getAssignedToUserId());
            if(user!=null){
                task.get().setAssignedToUser(user);
                task.get().setTitle(updateTaskDTO.getTitle());
                task.get().setDescription(updateTaskDTO.getDescription());
                task.get().setPriority(Priority.valueOf(updateTaskDTO.getPriority()));
                task.get().setDeadline(new Date(updateTaskDTO.getDeadline()));
                task.get().setStatus(Status.valueOf(updateTaskDTO.getStatus()));
                taskRepository.save(task.get());
                return true;
            }
        } else{
            throw new TaskNotFoundException(updateTaskDTO.getTaskId());
        }
        return false;
    }
}
