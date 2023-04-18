package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.User;

public class UserMapper {
    public static UserDTO convertModelToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setType(user.getType());
        userDTO.setDepartmentId(user.getDepartment().getDepartmentId());
        userDTO.setLocation(user.getLocation());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        if(userDTO.getType() == 2 && user.getMentor()!= null){
            userDTO.setMentorId(user.getMentor().getId());
        }
        return userDTO;
    }

    public static User convertDtoToModel(UserDTO userDTO, Department department){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setType(userDTO.getType());
        user.setDepartment(department);
        user.setLocation(userDTO.getLocation());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }

}
