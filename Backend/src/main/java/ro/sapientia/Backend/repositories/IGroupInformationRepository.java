package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.GroupInformation;

import java.util.List;

@Repository
public interface IGroupInformationRepository extends JpaRepository<GroupInformation, Long> {
    List<GroupInformation> findByUserId(Long userId);
}
