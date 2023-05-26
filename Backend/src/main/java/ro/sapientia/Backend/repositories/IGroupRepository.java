package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.Group;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
}
