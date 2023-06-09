package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.Group;
import ro.sapientia.Backend.repositories.IDepartmentRepository;
import ro.sapientia.Backend.repositories.IGroupRepository;
import ro.sapientia.Backend.services.IGroupService;
import ro.sapientia.Backend.services.exceptions.DepartmentNotFoundException;
import ro.sapientia.Backend.services.exceptions.GroupNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService implements IGroupService {
    private IGroupRepository groupRepository;

    public GroupService(IGroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }
    @Override
    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if(!group.isPresent()){
            throw new GroupNotFoundException(id);
        }
        return group.get();
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }
}
