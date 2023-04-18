package ro.sapientia.Backend.services.exceptions;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(Long id){
        super("Could not find department " + id);
    }
}
