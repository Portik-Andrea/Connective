package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.Backend.controllers.dto.AddMemberDTO;
import ro.sapientia.Backend.controllers.dto.GroupDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.GroupMapper;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.IGroupInformationService;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/groups")
public class GroupController {
    private final IGroupService groupService;
    private final IGroupInformationService groupInformationService;
    private final SecurityUserDetailsService userDetailsService;

    @Autowired
    public GroupController(IGroupService groupService, IGroupInformationService groupInformationService, SecurityUserDetailsService userDetailsService){
        this.groupService = groupService;
        this.groupInformationService = groupInformationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/getGroups")
    public List<GroupDTO> getGroups(){
        List<Group> groups = groupService.findAllGroups();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        groups.forEach(group -> groupDTOS.add(GroupMapper.convertModelToDTO(group)));
        return groupDTOS;
    }

    @GetMapping("/{groupId}")
    public List<UserDTO> getGroupMembers(@PathVariable("groupId") Long groupId){
        List<UserEntity> groupMembers = groupService.findAllMembersToGroup(groupId);
        List<UserDTO> membersDTO = new ArrayList<>();
        groupMembers.forEach(mentor -> membersDTO.add(UserMapper.convertModelToDTO(mentor)));
        return membersDTO;
    }
    @GetMapping("/myGroups")
    public List<GroupDTO> getGroups(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            List<Group> groups = groupService.findUsersGroups(id);
            List<GroupDTO> groupDTOS = new ArrayList<>();
            groups.forEach(group -> groupDTOS.add(GroupMapper.convertModelToDTO(group)));
            return groupDTOS;
        }
        else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @PostMapping("/addNewMember")
    public String addNewMembers(@RequestBody AddMemberDTO addMemberDTO,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            Group group = groupService.findById(addMemberDTO.getGroupId());
            if (!groupInformationService.addNewGroupInformation(addMemberDTO, group, id)) {
                return "Adding a new member to the group is unsuccessful";
            }
            return "Adding a new member to the group is successful";
        }else {
                throw new IllegalArgumentException("Invalid token");
        }
    }
}
