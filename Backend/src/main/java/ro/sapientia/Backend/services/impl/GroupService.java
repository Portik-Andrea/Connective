package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.repositories.IGroupRepository;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.GroupNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;
    private final IUserService userService;

    public GroupService(IGroupRepository groupRepository, IUserService userService){
        this.groupRepository = groupRepository;
        this.userService = userService;
    }
    @Override
    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if(group.isEmpty()){
            throw new GroupNotFoundException(id);
        }
        return group.get();
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllMembersToGroup(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty()){
            throw new GroupNotFoundException(groupId);
        }
        return group.get().getMembers().stream().toList();
    }

    @Override
    public List<Group> findUsersGroups(Long userId) {
        return userService.findUserByID(userId).getGroups().stream().toList();
    }
}
