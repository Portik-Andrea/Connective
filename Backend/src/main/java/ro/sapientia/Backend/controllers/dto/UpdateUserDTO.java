package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class UpdateUserDTO implements Serializable {
    @NotNull(message = "First name is mandatory")
    @Size(max = 50, message = "First name length max 50 character")
    private String firstName;
    @NotNull(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name length max 50 character")
    private String lastName;
    @Size(max = 250, message = "Location max 50 character")
    private String location;
    @Pattern(regexp = "^\\+\\d{11}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}$")
    private String phoneNumber;
    private String imageUrl;
}
