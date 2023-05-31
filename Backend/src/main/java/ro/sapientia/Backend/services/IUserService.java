package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.UpdateUserDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.UserEntity;

import java.util.List;

public interface IUserService {
    UserEntity findUserByID(Long id);

    UserEntity addUser(UserDTO userDTO);

    boolean updateUser(UpdateUserDTO updateUserDTO,Long id);

    List<UserEntity> allMentors();

    UserEntity addMentor(Long userId, Long mentorId);

    List<UserEntity> findAllUsers();
}
