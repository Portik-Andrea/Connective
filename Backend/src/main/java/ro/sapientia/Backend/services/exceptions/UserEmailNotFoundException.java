package ro.sapientia.Backend.services.exceptions;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String email){
        super("Could not find email " + email);
    }
}
