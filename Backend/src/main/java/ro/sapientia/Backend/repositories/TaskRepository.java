package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.Task;
import ro.sapientia.Backend.domains.UserEntity;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    UserEntity findByAssignedToUser(Long taskId);

    List<Task> findAll();

}
