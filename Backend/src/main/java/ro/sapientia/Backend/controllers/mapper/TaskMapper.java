package ro.sapientia.Backend.controllers.mapper;

import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.controllers.dto.UserDTO;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.domains.UserEntity;

import java.util.Base64;

public class TaskMapper {
    public static TaskDTO convertModelToDTO(Task task){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setAssignedToUserId(task.getAssignedToUser().getId());
        taskDTO.setAssignedToUserName(task.getAssignedToUser().getFirstName() + " " + task.getAssignedToUser().getLastName());
        taskDTO.setCreatorUserId(task.getCreatorUser().getId());
        taskDTO.setCreatorUserName(task.getCreatorUser().getFirstName() + " " + task.getCreatorUser().getLastName());
        taskDTO.setCreatedTime(task.getCreatedTime().getTime());
        taskDTO.setPriority(task.getPriority().toString());
        taskDTO.setDeadline(task.getDeadline().getTime());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setProgress(task.getProgress());
        return taskDTO;
    }
}
