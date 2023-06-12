package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskActivitiesDTO {
    @NotNull()
    private String title;
    private String assignedToUserName;
    private String creatorUserName;
    private String creatorUserImage;
    @NotEmpty(message = "The created time is mandatory")
    private Long createdTime;
    private String groupName;
}
