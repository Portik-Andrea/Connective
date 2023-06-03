package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RegisterRequestDTO {
    @NotEmpty(message = "First name is mandatory")
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;
    @NotNull(message = "User type is mandatory")
    @Pattern(regexp = "MENTOR|MENTEE",message = "User type value equal MENTOR or MENTEE")
    private String type;
    @NotNull(message = "Department id is mandatory")
    private Long departmentId;
    @NotEmpty(message = "Email is mandatory")
    @Size(max = 255)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email address is invalid")
    private String email;
    @Size(min = 8, message = "Password min 8 character")
    @NotEmpty(message = "Password is mandatory")
    private String password;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
