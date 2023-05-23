package ro.sapientia.Backend.services.exceptions;

public class IllegalUserTypeException extends RuntimeException{
    public IllegalUserTypeException(String type) {
        super("User type " + type + " is an invalid type");
    }
}
