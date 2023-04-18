package ro.sapientia.Backend.services.exceptions;

public class IllegalEmailException extends RuntimeException {
    public IllegalEmailException(String email) {
        super("The email address already exists: " + email);
    }
}
