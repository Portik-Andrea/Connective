package ro.sapientia.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.DepartmentService;
import ro.sapientia.Backend.services.UserService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public UserController(UserService userService, DepartmentService departmentService){
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") @Positive Long id){
        UserEntity user = userService.findUserByID(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return UserMapper.convertModelToDTO(user);
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        UserEntity result = userService.addUser(userDTO);
        return new ResponseEntity<>(
                "Success",
                HttpStatus.OK);
    }

}
