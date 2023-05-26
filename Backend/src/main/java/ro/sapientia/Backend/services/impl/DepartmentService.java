package ro.sapientia.Backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.repositories.IDepartmentRepository;
import ro.sapientia.Backend.services.IDepartmentService;
import ro.sapientia.Backend.services.exceptions.DepartmentNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentService implements IDepartmentService {

    private IDepartmentRepository departmentRepository;

    public DepartmentService(IDepartmentRepository departmentRepository){
        this.departmentRepository= departmentRepository;
    }

    @Override
    public Department findById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent()){
            throw new DepartmentNotFoundException(id);
        }
        return department.get();
    }
}
