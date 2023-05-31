package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.TaskMapper;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final ITaskService taskService;

    private final SecurityUserDetailsService userDetailsService;

    @Autowired
    public TaskController(ITaskService taskService, SecurityUserDetailsService userDetailsService){
        this.taskService = taskService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/myTasks")
    public List<TaskDTO> getMyTasks(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            List<Task> userTasks = taskService.findAllByAssigneeUser(id);
            List<TaskDTO> userTasksDTO = new ArrayList<>();
            userTasks.forEach(task -> userTasksDTO.add(TaskMapper.convertModelToDTO(task)));
            return userTasksDTO;
        }
        else{
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @GetMapping("/allTasks")
    public List<TaskDTO> getAllTasks(){
        List<Task> tasks = taskService.findAll();
        List<TaskDTO> tasksDTO = new ArrayList<>();
        tasks.forEach(task -> tasksDTO.add(TaskMapper.convertModelToDTO(task)));
        return tasksDTO;
    }
    @GetMapping("/getTask/{taskId}")
    public TaskDTO getTask(@PathVariable("taskId") Long taskId){
        Task task = taskService.findById(taskId);
        return TaskMapper.convertModelToDTO(task);
    }

}
