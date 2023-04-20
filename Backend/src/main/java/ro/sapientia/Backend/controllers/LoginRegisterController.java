package ro.sapientia.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ro.sapientia.Backend.controllers.dto.LoginRequest;
import ro.sapientia.Backend.controllers.dto.LoginResponse;
import ro.sapientia.Backend.controllers.dto.RegisterRequestDTO;
import ro.sapientia.Backend.services.security.JwtTokenService;
import ro.sapientia.Backend.services.security.SecurityUserDetailsService;

import javax.validation.Valid;

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

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            //log.info("Bad credentials");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final LoginResponse loginResponse = new LoginResponse();
        String token = tokenService.generateToken(userDetails);
        loginResponse.setToken(token);
        userDetailsService.saveUserToken(authenticationRequest.getEmail(), token);
        loginResponse.setUserId(userDetailsService.sendUserId(token));
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
}
