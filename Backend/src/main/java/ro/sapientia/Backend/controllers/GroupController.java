package ro.sapientia.Backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sapientia.Backend.controllers.dto.GroupDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.GroupMapper;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.IUserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/groups")
public class GroupController {
    private final IGroupService groupService;

    @Autowired
    public GroupController(IGroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/getGroups")
    public List<GroupDTO> getGroups(){
        List<Group> groups = groupService.findAllGroups();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        groups.forEach(group -> groupDTOS.add(GroupMapper.convertModelToDTO(group)));
        return groupDTOS;
    }
}
