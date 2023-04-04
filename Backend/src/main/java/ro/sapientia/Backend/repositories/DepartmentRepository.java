package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
