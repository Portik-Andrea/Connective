package ro.sapientia.Backend.controllers.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.sapientia.Backend.domains.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    @NotNull
    private Long id;
    @NotNull
    @Size(max = 50)
    private String firstName;
    @NotNull
    @Size(max = 50)
    private String lastName;
    @NotNull(message = "email is mandatory")
    private String email;

    private String type;
    private Long departmentId;
    private String departmentName;
    @Size(max = 250)
    private String location;

    private String phoneNumber;

    private String imageUrl;

    private Long mentorId;
}
