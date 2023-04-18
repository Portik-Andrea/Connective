package ro.sapientia.Backend.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterRequestDTO {
    @NotEmpty(message = "First name is mandatory")
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;
    @NotEmpty(message = "email is mandatory")
    @Size(max = 255)
    private String email;
    @Size(max = 255)
    @NotEmpty(message = "password is mandatory")
    private String password;
}
