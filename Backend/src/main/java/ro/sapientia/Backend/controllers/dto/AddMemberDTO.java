package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddMemberDTO {
    @NotNull(message = "Group id is mandatory")
    private Long groupId;
    @NotNull(message = "Invited user id is mandatory")
    private Long userId;
}
