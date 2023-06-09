package ro.sapientia.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.Backend.controllers.dto.UpdateUserDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final IUserService userService;
    private final SecurityUserDetailsService userDetailsService;

    @Autowired
    public UserController(IUserService userService,
                          SecurityUserDetailsService userDetailService){
        this.userService = userService;
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
    @GetMapping("/myUser")
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

    @GetMapping("/getAllUsers")
    public List<UserDTO> getAllUsers(){
        List<UserEntity> users = userService.findAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(UserMapper.convertModelToDTO(user)));
        return userDTOS;
    }
    @PostMapping("/updateUser")
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

    @GetMapping("/mentors")
    public List<UserDTO> allMentors(){
        List<UserEntity> mentors = userService.allMentors();
        List<UserDTO> mentorsDTO = new ArrayList<>();
        mentors.forEach(mentor -> mentorsDTO.add(UserMapper.convertModelToDTO(mentor))
        );
        return mentorsDTO;
    }

    @GetMapping("/selectMentor/{mentorId}")
    public UserDTO selectMentor(@PathVariable("mentorId") Long mentorId, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long id = userDetailsService.sendUserId(token);
            UserEntity mentor = userService.addMentor(id,mentorId);
            if(mentor == null){
                throw new UserNotFoundException(mentorId);
            }
            return UserMapper.convertModelToDTO(mentor);
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
}
