package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.AddMemberDTO;
import ro.sapientia.Backend.domains.Group;

public interface IGroupInformationService {
    boolean addNewGroupInformation(AddMemberDTO addMemberDTO, Group group, Long invitingUserId);
}
