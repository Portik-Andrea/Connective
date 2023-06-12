package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.controllers.dto.AddMemberDTO;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.GroupInformation;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.repositories.IGroupInformationRepository;
import ro.sapientia.Backend.repositories.IGroupRepository;
import ro.sapientia.Backend.services.IGroupInformationService;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.IUserService;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GroupInformationService implements IGroupInformationService {

    private final IGroupInformationRepository groupInformationRepository;
    private final IUserService userService;
    //private final IGroupService groupService;

    public GroupInformationService(IGroupInformationRepository groupInformationRepository, IUserService userService/*, IGroupService groupService*/) {
        this.groupInformationRepository = groupInformationRepository;
        this.userService = userService;
        //this.groupService = groupService;
    }
    @Override
    @Transactional(readOnly = false)
    public boolean addNewGroupInformation(AddMemberDTO addMemberDTO,Group group, Long invitingUserId) {
        UserEntity user = userService.findUserByID(addMemberDTO.getUserId());
        //Group group = groupService.findById(addMemberDTO.getGroupId());
        UserEntity invitingUser = userService.findUserByID(invitingUserId);
        if(user == null || group == null || invitingUser == null){
            return false;
        }
        GroupInformation groupInformation = new GroupInformation();
        groupInformation.setUser(user);
        groupInformation.setGroup(group);
        groupInformation.setJoiningDate(new Date());
        groupInformation.setInvitingUser(invitingUser);
        groupInformationRepository.save(groupInformation);
        return true;
    }

    @Override
    public List<GroupInformation> findByUserId(Long userId) {
        return groupInformationRepository.findByUserId(userId);
    }
}
