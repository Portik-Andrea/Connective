package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.AddMemberDTO;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.UserEntity;

import java.util.List;

public interface IGroupService {
    Group findById(Long id);
    List<Group> findAllGroups();
    List<UserEntity> findAllMembersToGroup(Long groupId);

    List<Group> findUsersGroups(Long userId);
}
