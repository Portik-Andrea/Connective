package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.controllers.dto.CreateTaskDTO;
import ro.sapientia.Backend.controllers.dto.UpdateTaskDTO;
import ro.sapientia.Backend.domains.*;
import ro.sapientia.Backend.repositories.ITaskRepository;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.GroupNotFoundException;
import ro.sapientia.Backend.services.exceptions.TaskNotFoundException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    private final IUserService userService;

    private final IGroupService groupService;

    public TaskService(ITaskRepository taskRepository, UserService userService, GroupService groupService){
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.groupService = groupService;
    }
    @Override
    public List<Task> findAllByAssigneeUser(Long userId) {
        if(userService.findUserByID(userId)!= null){
            return taskRepository.findAllByAssignedToUserId(userId);
        }
        else{
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
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
            Group group = groupService.findById(updateTaskDTO.getGroupId());
            if(user!=null){
                if(group!=null){
                    task.get().setAssignedToUser(user);
                    task.get().setTitle(updateTaskDTO.getTitle());
                    task.get().setDescription(updateTaskDTO.getDescription());
                    task.get().setGroup(group);
                    task.get().setPriority(Priority.valueOf(updateTaskDTO.getPriority()));
                    task.get().setDeadline(new Date(updateTaskDTO.getDeadline()));
                    task.get().setStatus(Status.valueOf(updateTaskDTO.getStatus()));
                    task.get().setProgress(setProgress(task.get().getStatus(),task.get().getProgress()));
                    taskRepository.save(task.get());
                    return true;
                }
                else{
                    throw new GroupNotFoundException(updateTaskDTO.getGroupId());
                }
            }
        } else{
            throw new TaskNotFoundException(updateTaskDTO.getTaskId());
        }
        return false;
    }

    private int setProgress(Status status,int currentProgressValue){
        return switch (status) {
            case NEW -> 0;
            case IN_PROGRESS -> 50;
            case DONE -> 100;
            default -> currentProgressValue;
        };
    }

    @Override
    @Transactional(readOnly = false)
    public boolean createTask(CreateTaskDTO createTaskDTO, Long creatorUserId) {
        UserEntity creatorUser = userService.findUserByID(creatorUserId);
        UserEntity user = userService.findUserByID(createTaskDTO.getAssignedToUserId());
        Group group = groupService.findById(createTaskDTO.getGroupId());
        Task task = new Task();
        if(user!=null && creatorUser!=null && creatorUser.getType()== UserType.MENTOR){
            if(group!=null){
                task.setCreatorUser(creatorUser);
                task.setAssignedToUser(user);
                task.setTitle(createTaskDTO.getTitle());
                task.setDescription(createTaskDTO.getDescription());
                task.setCreatedTime(new Date());
                task.setGroup(group);
                task.setPriority(Priority.valueOf(createTaskDTO.getPriority()));
                task.setDeadline(new Date(createTaskDTO.getDeadline()));
                task.setStatus(Status.valueOf(createTaskDTO.getStatus()));
                task.setProgress(0);
                taskRepository.save(task);
                return true;
            }
            else{
                throw new GroupNotFoundException(createTaskDTO.getGroupId());
            }
        }else{
            return false;
        }
    }
}
