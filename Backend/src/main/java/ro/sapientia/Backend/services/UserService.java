package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.UserEntity;

public interface UserService {
    UserEntity findUserByID(Long id);

    UserEntity addUser(UserDTO userDTO);
}
