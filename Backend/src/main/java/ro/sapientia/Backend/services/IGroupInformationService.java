package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.AddMemberDTO;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.domains.GroupInformation;

import java.util.List;

public interface IGroupInformationService {
    boolean addNewGroupInformation(AddMemberDTO addMemberDTO, Group group, Long invitingUserId);

    List<GroupInformation> findByUserId(Long userId);
}
