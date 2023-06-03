package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.sapientia.Backend.domains.Status;

/*import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;*/
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UpdateTaskDTO  implements Serializable {
    @NotNull(message = "Task id is mandatory")
    private Long taskId;

    @NotNull(message = "Title is null")
    @Size(min=3, max = 30)
    private String title;

    @NotEmpty(message = "Description is mandatory")
    @Size(min = 10, max = 300)
    private String description;

    @NotNull(message = "Assigned to user id is mandatory")
    private Long assignedToUserId;

    @NotEmpty(message = "Priority is mandatory")
    @Pattern(regexp = "HIGH|MEDIUM|LOW",message = "Priority equal HIGH, MEDIUM or LOW")
    private String priority;

    @NotNull(message = "Deadline is mandatory")
    private Long deadline;

    @NotNull(message = "Status is mandatory")
    @Pattern(regexp = "NEW|IN_PROGRESS|DONE|BLOCKED",message = "Priority equal HIGH, MEDIUM or LOW")
    private String status;
}
