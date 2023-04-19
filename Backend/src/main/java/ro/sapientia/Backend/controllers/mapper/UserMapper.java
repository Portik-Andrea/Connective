package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.UserEntity;

public class UserMapper {
    public static UserDTO convertModelToDTO(UserEntity user){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setType(user.getType());
        if(user.getDepartment()!=null){
            userDTO.setDepartmentId(user.getDepartment().getDepartmentId());
        }
        userDTO.setLocation(user.getLocation());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        if(userDTO.getType() == 2 && user.getMentor()!= null){
            userDTO.setMentorId(user.getMentor().getId());
        }
        return userDTO;
    }

    public static UserEntity convertDtoToModel(UserDTO userDTO, Department department){
        UserEntity user = new UserEntity();
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
