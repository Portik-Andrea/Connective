package ro.sapientia.Backend.controllers.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.sapientia.Backend.domains.Priority;
import ro.sapientia.Backend.domains.Status;
import ro.sapientia.Backend.domains.UserEntity;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class TaskDTO {
    @NotNull
    private Long taskId;

    @NotEmpty(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    @NotNull
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
    @Max(100)
    @Min(0)
    private Integer progress;
}
