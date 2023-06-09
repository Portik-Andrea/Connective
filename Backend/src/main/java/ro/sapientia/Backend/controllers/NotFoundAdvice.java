package ro.sapientia.Backend.controllers;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.sapientia.Backend.services.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class NotFoundAdvice{
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserEmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userEmailNotFoundHandler(UserEmailNotFoundException ex){ return  ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String departmentNotFoundHandler(DepartmentNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalUserTypeException.class)
    @ResponseStatus(BAD_REQUEST)
    public String illegalUserTypeHandler(IllegalUserTypeException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalEmailException.class)
    @ResponseStatus(BAD_REQUEST)
    public String illegalEmailHandler(IllegalEmailException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskNotFoundHandler(TaskNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String groupNotFoundHandler(GroupNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public String handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        //ResponseEntity<String> response = new ResponseEntity<>(errors.values().toString(),HttpStatus.BAD_REQUEST);
        return errors.values().toString();
    }
}
