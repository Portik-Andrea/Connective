package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserActivitiesDTO {
    @NotNull
    private String name;
    @NotNull
    private String mentorName;
    private String mentorImage;
    @NotNull
    private String departmentName;
    private String location;
    private String imageUrl;
}
