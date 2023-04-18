package ro.sapientia.Backend.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.domains.User;
import ro.sapientia.Backend.repositories.UserRepository;
import ro.sapientia.Backend.services.UserService;
import ro.sapientia.Backend.services.exceptions.IllegalEmailException;
import ro.sapientia.Backend.services.exceptions.IllegalUserTypeException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.List;
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

    @Override
    @Transactional(readOnly = false)
    public User addUser(User user) {
        User mentor = user.getMentor();
        if(user.getType() == 2)
            if(mentor.getType()==1) {
                boolean existEmail = userRepository.existsByEmail(user.getEmail());
                if (existEmail) {
                    throw new IllegalEmailException(user.getEmail());
                } else {
                    return userRepository.save(user);
                }
            }
            else {
                throw new IllegalUserTypeException(mentor.getType(), "user is not mentor");
            }
        else{
            throw new IllegalUserTypeException(user.getType(), "user is not mentee");
        }
    }
}
