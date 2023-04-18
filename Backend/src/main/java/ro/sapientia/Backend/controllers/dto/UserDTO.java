package ro.sapientia.Backend.controllers.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.User;

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
    @NotNull
    private Long departmentId;
    @Size(max = 250)
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    private String imageUrl;

    private Long mentorId;
}
