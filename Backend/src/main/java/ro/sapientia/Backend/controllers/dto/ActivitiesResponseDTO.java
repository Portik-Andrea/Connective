package ro.sapientia.Backend.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ActivitiesResponseDTO {
    private List<TaskActivitiesDTO> tasks;
    private List<GroupActivitiesDTO> groups;
    private List<UserActivitiesDTO> users;
}
