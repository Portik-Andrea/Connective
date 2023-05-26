package ro.sapientia.Backend.services;

import ro.sapientia.Backend.domains.Department;

public interface IDepartmentService {
    Department findById(Long id);
}
