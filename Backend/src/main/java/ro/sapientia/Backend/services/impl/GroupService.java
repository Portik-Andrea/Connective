package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.services.IGroupService;

@Service
@Transactional(readOnly = true)
public class GroupService implements IGroupService {
}