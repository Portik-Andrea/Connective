package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sapientia.Backend.controllers.dto.ActivitiesResponseDTO;
import ro.sapientia.Backend.controllers.dto.GroupActivitiesDTO;
import ro.sapientia.Backend.controllers.dto.TaskActivitiesDTO;
import ro.sapientia.Backend.controllers.dto.UserActivitiesDTO;
import ro.sapientia.Backend.controllers.mapper.GroupMapper;
import ro.sapientia.Backend.controllers.mapper.TaskMapper;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.GroupInformation;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.IGroupInformationService;
import ro.sapientia.Backend.services.ITaskService;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/activities")
public class ActivitiesController {
    private final IUserService userService;
    private final ITaskService taskService;

    private final IGroupInformationService groupInformationService;

    private final SecurityUserDetailsService userDetailsService;

    @Autowired
    public ActivitiesController(IUserService userService, ITaskService taskService, IGroupInformationService groupInformationService, SecurityUserDetailsService userDetailsService) {
        this.userService = userService;
        this.taskService = taskService;
        this.groupInformationService = groupInformationService;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/getTaskActivities")
    public List<TaskActivitiesDTO> getTaskActivities(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            List<Task> tasks = taskService.findAllByAssigneeUser(id);

            List<TaskActivitiesDTO> tasksDTO = new ArrayList<>();
            tasks.forEach(task ->tasksDTO.add(TaskMapper.convertModelToActivitiesDTO(task)));


            return tasksDTO;
        }else {
                throw new IllegalArgumentException("Invalid token");
        }
    }
    @GetMapping("/getGroupActivities")
    public List<GroupActivitiesDTO> getGroupActivities(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            List<GroupInformation> groups = groupInformationService.findByUserId(id);
            List<GroupActivitiesDTO> groupsDTO = new ArrayList<>();
            groups.forEach(group -> groupsDTO.add(GroupMapper.convertModelToActivitiesDTO(group)));
            return groupsDTO;
        }else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @GetMapping("/getUserActivities")
    public List<UserActivitiesDTO> getData(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            List<UserEntity> users = userService.findUsers();
            List<UserActivitiesDTO> usersDTO = new ArrayList<>();
            users.forEach(user -> usersDTO.add(UserMapper.convertModelToActivitiesDTO(user)));
            return usersDTO;
        }else {
            throw new IllegalArgumentException("Invalid token");
        }
    }
}
