package ro.sapientia.Backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.sapientia.Backend.services.exceptions.DepartmentNotFoundException;
import ro.sapientia.Backend.services.exceptions.IllegalEmailException;
import ro.sapientia.Backend.services.exceptions.IllegalUserTypeException;
import ro.sapientia.Backend.services.exceptions.UserNotFoundException;

@ControllerAdvice
public class NotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String departmentNotFoundHandler(DepartmentNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalUserTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalUserTypeHandler(IllegalUserTypeException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalEmailHandler(IllegalEmailException ex){
        return ex.getMessage();
    }
}
