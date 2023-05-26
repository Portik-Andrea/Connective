package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.UpdateUserDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.domains.UserType;

import java.util.Base64;

public class UserMapper {
    public static UserDTO convertModelToDTO(UserEntity user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setType(user.getType().toString());
        if(user.getDepartment()!=null){
            userDTO.setDepartmentId(user.getDepartment().getDepartmentId());
            userDTO.setDepartmentName(user.getDepartment().getDepartmentName());
        }
        userDTO.setLocation(user.getLocation());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        if(user.getImageUrl()!=null){
            userDTO.setImageUrl(Base64.getMimeEncoder().encodeToString(user.getImageUrl()));
        }
        if(user.getMentor()!= null){
            userDTO.setMentorId(user.getMentor().getId());
        }
        return userDTO;
    }

    public static UserEntity convertDtoToModel(UserDTO userDTO, Department department){
        UserEntity user = new UserEntity();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setType(UserType.valueOf(userDTO.getType()));
        user.setDepartment(department);
        user.setLocation(userDTO.getLocation());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
    public static UserEntity convertUpdateUserDtoToModel(UpdateUserDTO updateUserDTO,UserEntity user){
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setLocation(updateUserDTO.getLocation());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        user.setImageUrl(Base64.getMimeDecoder().decode(updateUserDTO.getImageUrl()));
        return user;
    }

}
