package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.Backend.controllers.dto.UpdateUserDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.DepartmentService;
import ro.sapientia.Backend.services.UserService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    public UserController(UserService userService, DepartmentService departmentService,
                          SecurityUserDetailsService userDetailService){
        this.userService = userService;
        this.departmentService = departmentService;
        this.userDetailsService = userDetailService;
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") @Positive Long id){
        UserEntity user = userService.findUserByID(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return UserMapper.convertModelToDTO(user);
    }
    @GetMapping("/myuser")
    public UserDTO getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            UserEntity user = userService.findUserByID(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            return UserMapper.convertModelToDTO(user);
        }
        else {
            throw new IllegalArgumentException("Invalid token");
        }
    }
    @PostMapping("/updateuser")
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateUserDTO updateUserDTO,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            boolean result = userService.updateUser(updateUserDTO,id);

            if(!result){
                return new ResponseEntity<>(
                        Boolean.FALSE,
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    Boolean.TRUE,
                    HttpStatus.OK);
        }
        else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        UserEntity result = userService.addUser(userDTO);
        return new ResponseEntity<>(
                "Success",
                HttpStatus.OK);
    }

//    @GetMapping("/user")
//    public UserDTO getUser(){
//
//        UserEntity user = userService.findUserByID(id);
//        if(user == null){
//            throw new UserNotFoundException(id);
//        }
//        return UserMapper.convertModelToDTO(user);
//    }
}
