package ro.sapientia.Backend.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "departments")
public class Department {

    @Id
    @SequenceGenerator(name = "department_id_gen", sequenceName = "department_id_seq", initialValue = 1)
    @GeneratedValue(generator = "department_id_gen")
    private Long departmentId;

    @Column(name = "department_name")
    @NotEmpty(message = "Department name is mandatory")
    private String departmentName;

    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
