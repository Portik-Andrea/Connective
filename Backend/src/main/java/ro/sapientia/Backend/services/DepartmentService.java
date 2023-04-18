package ro.sapientia.Backend.services;

import ro.sapientia.Backend.domains.Department;

public interface DepartmentService {
    Department findById(Long id);
}
