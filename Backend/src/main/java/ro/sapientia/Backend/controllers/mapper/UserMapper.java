package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.User;

public class UserMapper {
    public static UserDTO convertModelToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setType(user.getType());
        userDTO.setDepartmentName(user.getDepartment().getDepartmentName());
        userDTO.setLocation(user.getLocation());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        if(userDTO.getType() == 2 && user.getMentor()!= null){
            userDTO.setMentorId(user.getMentor().getId());
        }
        return userDTO;
    }
}
