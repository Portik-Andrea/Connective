package ro.sapientia.Backend.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.domains.User;
import ro.sapientia.Backend.repositories.UserRepository;
import ro.sapientia.Backend.services.UserService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository= userRepository;
    }
    @Override
    public User findUserByID(Long id) throws ServiceException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(id);
        }
        return user.get();
    }
}
