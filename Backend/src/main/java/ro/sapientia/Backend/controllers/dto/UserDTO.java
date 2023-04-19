package ro.sapientia.Backend.controllers.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    @NotNull
    @Size(max = 50)
    private String firstName;
    @NotNull
    @Size(max = 50)
    private String lastName;
    @NotNull(message = "email is mandatory")
    private String email;
    @Positive
    private Integer type;
    private Long departmentId;
    @Size(max = 250)
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    private String imageUrl;

    private Long mentorId;
}
