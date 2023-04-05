package ro.sapientia.Backend.services.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("Could not find user " + id);
    }
}
