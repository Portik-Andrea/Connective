package ro.sapientia.Backend.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.controllers.dto.UpdateUserDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.domains.UserType;
import ro.sapientia.Backend.repositories.IDepartmentRepository;
import ro.sapientia.Backend.repositories.IUserRepository;
import ro.sapientia.Backend.services.IUserService;
import ro.sapientia.Backend.services.exceptions.DepartmentNotFoundException;
import ro.sapientia.Backend.services.exceptions.IllegalEmailException;
import ro.sapientia.Backend.services.exceptions.IllegalUserTypeException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {
    private IUserRepository userRepository;

    private IDepartmentRepository departmentRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserEntity findUserByID(Long id) throws ServiceException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    @Override
    @Transactional(readOnly = false)
    public UserEntity addUser(UserDTO userDTO) {
        if(Objects.equals(userDTO.getType(), UserType.MENTEE.toString()) || Objects.equals(userDTO.getType(), UserType.MENTOR.toString())) {
            boolean existEmail = userRepository.existsByEmail(userDTO.getEmail());
            if (existEmail) {
                throw new IllegalEmailException(userDTO.getEmail());
            } else {
                Optional<Department> department = departmentRepository.findById(userDTO.getDepartmentId());
                if(department.isPresent() ){
                    UserEntity user = UserMapper.convertDtoToModel(userDTO,department.get());
                    return userRepository.save(user);
                }
                else{
                    throw new DepartmentNotFoundException(userDTO.getDepartmentId());
                }
            }
        }
        else{
            throw new IllegalUserTypeException(userDTO.getType());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateUser(UpdateUserDTO updateUserDTO,Long id) {
        if(updateUserDTO == null){
            throw new IllegalArgumentException("User data is null");
        }
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()){
            UserEntity updateUser = UserMapper.convertUpdateUserDtoToModel(updateUserDTO,user.get());
            userRepository.save(updateUser);
            return true;
        }
        return false;
    }

    @Override
    public List<UserEntity> allMentors(){
        List<UserEntity> mentors = userRepository.findAllByType(UserType.MENTOR);
        return mentors;
    }

    @Override
    @Transactional(readOnly = false)
    public UserEntity addMentor(Long userId, Long mentorId){
        UserEntity user = findUserByID(userId);
        //byte[] decodeId = Base64.getMimeDecoder().decode(mentorId);
        /*var decodeId = Base64Utils.decodeFromString(mentorId);
        ByteBuffer buffer = ByteBuffer.wrap(decodeId);
        buffer.order(ByteOrder.BIG_ENDIAN);
        //Long decodeLongId = 1L;
        Long decodeLongId = buffer.getLong();*/
        UserEntity mentor = findUserByID(mentorId);
        if(user.getMentor()== null) {
            user.setMentor(mentor);
            userRepository.save(user);
        }
        return mentor;
    }

}
