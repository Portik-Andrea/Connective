package ro.sapientia.Backend.services.exceptions;

public class GroupNotFoundException extends RuntimeException{

    public GroupNotFoundException(Long id){
        super("Could not find group " + id);
    }
}
