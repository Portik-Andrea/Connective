package ro.sapientia.Backend.services.exceptions;

public class IllegalUserTypeException extends RuntimeException{
    public IllegalUserTypeException(int type, String message) {
        super("User type " + type + " is an invalid type, " + message);
    }
}
