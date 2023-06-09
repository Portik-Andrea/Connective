package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.GroupDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.UserEntity;

public class GroupMapper {
    public static GroupDTO convertModelToDTO(Group group){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        return groupDTO;
    }
}
