package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupDTO {
    @NotNull(message = "Group id is mandatory")
    private Long groupId;
    @NotEmpty(message = "Group name is mandatory")
    private String groupName;
}
