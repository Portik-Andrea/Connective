package ro.sapientia.Backend.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.sapientia.Backend.controllers.dto.RegisterRequestDTO;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.domains.UserType;
import ro.sapientia.Backend.repositories.DepartmentRepository;
import ro.sapientia.Backend.repositories.UserRepository;
import ro.sapientia.Backend.services.exceptions.DepartmentNotFoundException;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private DepartmentRepository departmentRepository;


    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository,DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        //this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if( !userEntity.isPresent() ){
            throw new UsernameNotFoundException(email);
        }
        UserDetails user = User.withUsername(userEntity.get().getEmail())
                        .password(userEntity.get().getPassword())
                        .authorities("USER").build();

        return user;
    }

    public boolean checkUsername(String email){
        return userRepository.existsByEmail(email);
    }

    public void saveUser(RegisterRequestDTO registerRequest){
        UserEntity user = new UserEntity();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setType(UserType.valueOf(registerRequest.getType()));
        user.setPassword( passwordEncoder.encode(registerRequest.getPassword()) );
        //user.getRoles().add( roleRepository.findByName(ROLE_USER).orElseThrow() );
        userRepository.save( user );
    }
    public void saveUserToken(String email, String token){
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if( !user.isPresent() ){
            throw new UsernameNotFoundException(email);
        }
        user.get().setToken(token);
        userRepository.save( user.get() );
    }

    public Long sendUserId(String token){
        Optional<UserEntity> user = userRepository.findByToken(token);
        if( !user.isPresent() ){
            throw new UsernameNotFoundException(token);
        }
        return user.get().getId();
    }
}
