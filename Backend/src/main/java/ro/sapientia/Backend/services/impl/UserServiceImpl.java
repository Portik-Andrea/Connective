package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.services.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
}
