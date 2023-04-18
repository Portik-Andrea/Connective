package ro.sapientia.Backend.services;

import ro.sapientia.Backend.domains.User;

public interface UserService {
    User findUserByID(Long id);

    User addUser(User user);
}
