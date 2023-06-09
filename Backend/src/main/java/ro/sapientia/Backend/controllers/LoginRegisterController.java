package ro.sapientia.Backend.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.sapientia.Backend.controllers.dto.LoginRequest;
import ro.sapientia.Backend.controllers.dto.LoginResponse;
import ro.sapientia.Backend.controllers.dto.RegisterRequestDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.controllers.mapper.UserMapper;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;
import ro.sapientia.Backend.services.security.JwtTokenService;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;


@Slf4j
@RestController
@RequestMapping("api/v1/public")
public class LoginRegisterController {
    private AuthenticationManager authenticationManager;
    private SecurityUserDetailsService userDetailsService;
    private JwtTokenService tokenService;

    @Autowired
    public LoginRegisterController(AuthenticationManager authenticationManager,
                                   SecurityUserDetailsService userDetailService, JwtTokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailService;
        this.tokenService = tokenService;
    }
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid final LoginRequest authenticationRequest) {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()));
            log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (final BadCredentialsException ex) {
            log.info("Bad credentials");
            log.info("-----------------------------------------------------");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Bad credentials");
        }
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final LoginResponse loginResponse = new LoginResponse();
        String token = tokenService.generateToken(userDetails);
        loginResponse.setToken(token);
        userDetailsService.saveUserToken(authenticationRequest.getEmail(), token);
        loginResponse.setType(userDetailsService.sendUsertype(token));
        return loginResponse;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid final RegisterRequestDTO registerRequest) {

        if(userDetailsService.checkUsername(registerRequest.getEmail())) {
            return "Unsuccessful registration: email already taken";
        }
        userDetailsService.saveUser(registerRequest);
        return "Successful registration";
    }
    @GetMapping("/test")
    public ResponseEntity<String> getTest(){
        return new ResponseEntity<>(
                "Success",
                HttpStatus.OK);
    }
}
