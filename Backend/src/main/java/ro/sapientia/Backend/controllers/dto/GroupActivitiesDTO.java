package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupActivitiesDTO {
    @NotNull
    private String groupName;
    @NotNull
    private Long joiningDate;
    @NotNull
    private String invitingUserName;
    private String invitingUserImage;

}
