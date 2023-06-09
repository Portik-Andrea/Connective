package ro.sapientia.Backend.controllers.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.sapientia.Backend.domains.Status;

@Data
@NoArgsConstructor
public class CreateTaskDTO {
    @NotEmpty(message = "Title is mandatory")
    @Size(min=3, max = 100, message = "Title size must be between 3 and 100")
    private String title;

    @NotEmpty(message = "Description is mandatory")
    @Size(min = 10, max = 1000, message = "Description size must be between 10 and 1000")
    private String description;

    @NotNull(message = "Assigned to user id is mandatory")
    private Long assignedToUserId;

    @NotNull(message = "The group id is mandatory")
    private Long groupId;

    @NotEmpty(message = "Priority is mandatory")
    @Pattern(regexp = "HIGH|MEDIUM|LOW",message = "Priority equal HIGH, MEDIUM or LOW")
    private String priority;

    @NotNull(message = "Deadline is mandatory")
    private Long deadline;

    @NotNull(message = "Status is mandatory")
    @Pattern(regexp = "NEW|IN_PROGRESS|DONE|BLOCKED",message = "Status equal NEW, IN_PROGRESS, DONE, BLOCKED")
    private String status;
}
