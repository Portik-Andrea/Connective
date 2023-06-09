package ro.sapientia.Backend.services;

import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.Group;

import java.util.List;

public interface IGroupService {
    Group findById(Long id);
    List<Group> findAllGroups();
}
