package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.GroupActivitiesDTO;
import ro.sapientia.Backend.controllers.dto.GroupDTO;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.GroupInformation;

import java.util.Base64;

public class GroupMapper {
    public static GroupDTO convertModelToDTO(Group group){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        return groupDTO;
    }
    public static GroupActivitiesDTO convertModelToActivitiesDTO(GroupInformation groupInformation){
        GroupActivitiesDTO groupDTO = new GroupActivitiesDTO();
        groupDTO.setGroupName(groupInformation.getGroup().getGroupName());
        groupDTO.setJoiningDate(groupInformation.getJoiningDate().getTime());
        groupDTO.setInvitingUserName(groupInformation.getInvitingUser().getFirstName()+" "+groupInformation.getInvitingUser().getLastName());
        if(groupInformation.getInvitingUser().getImageUrl()!=null){
            groupDTO.setInvitingUserImage(Base64.getMimeEncoder().encodeToString(groupInformation.getInvitingUser().getImageUrl()));
        }
        return groupDTO;
    }
}
