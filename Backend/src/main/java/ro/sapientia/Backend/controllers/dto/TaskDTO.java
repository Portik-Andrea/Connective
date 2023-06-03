package ro.sapientia.Backend.controllers.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.sapientia.Backend.domains.Priority;
import ro.sapientia.Backend.domains.Status;
import ro.sapientia.Backend.domains.UserEntity;

import java.util.Date;

@Data
@NoArgsConstructor
public class TaskDTO {
    @NotNull(message = "Task id is mandatory")
    private Long taskId;
    @NotEmpty(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Assigned to user id is mandatory")
    private Long assignedToUserId;
    private String assignedToUserName;
    @NotEmpty(message = "Creator is mandatory")
    private Long creatorUserId;
    private String creatorUserName;
    @NotEmpty(message = "The created time is mandatory")
    private Long createdTime;
    private String priority;
    private Long deadline;
    private Status status;
    @Max(value = 100,message = "Progress value max 100")
    @Min(value = 0, message = "Progress value min 0")
    private Integer progress;
}
