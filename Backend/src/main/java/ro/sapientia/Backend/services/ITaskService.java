package ro.sapientia.Backend.services;

import ro.sapientia.Backend.controllers.dto.TaskDTO;
import ro.sapientia.Backend.domains.Task;

import java.util.List;

public interface ITaskService {
    List<Task> findAllByAssigneeUser(Long userId);

    List<Task> findAll();
}