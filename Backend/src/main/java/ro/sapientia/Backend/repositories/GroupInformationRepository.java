package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.GroupInformation;

@Repository
public interface GroupInformationRepository extends JpaRepository<GroupInformation, Long> {
}
