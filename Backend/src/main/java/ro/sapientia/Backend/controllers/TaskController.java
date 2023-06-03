package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.controllers.dto.UpdateTaskDTO;
import ro.sapientia.Backend.controllers.mapper.TaskMapper;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody @Valid UpdateTaskDTO updateTaskDTO){
        boolean result = taskService.updateTask(updateTaskDTO);
        return ResponseEntity.ok("Sikeres mentes " + result);

    }

}
