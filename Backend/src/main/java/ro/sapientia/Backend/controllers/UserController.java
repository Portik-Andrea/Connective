package ro.sapientia.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.User;
import ro.sapientia.Backend.services.UserService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") @Positive Long id){
        User user = userService.findUserByID(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return UserMapper.convertModelToDTO(user);
    }
}
