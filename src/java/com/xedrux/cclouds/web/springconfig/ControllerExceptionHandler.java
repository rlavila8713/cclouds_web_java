package com.xedrux.cclouds.web.springconfig;

import com.xedrux.cclouds.web.exceptions.EntityNotFoundException;
import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.util.HashMap;
import java.util.LinkedList;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *This class contains all methods that hanldes exceptions thrown in all
 * controllers.
 * @author Isidro Rodríguez Gamez
 */
@ControllerAdvice("com.xedrux.cclouds.web.controllers")
public class ControllerExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public HashMap<String, Object> handleEntityNotFoundException(
            EntityNotFoundException e) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public HashMap<String, Object> handleDuplicateKeyException(
            DuplicateKeyException exception) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return response;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public HashMap<String, Object> handleException(
            DataIntegrityViolationException exception) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnableToCreateEntityException.class)
    @ResponseBody
    public HashMap<String, Object> handleUnableToCreateUserException(
            UnableToCreateEntityException e) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        LinkedList<String> errors = new LinkedList<>();
        for (FieldError error : e.getList()) {
            errors.add(error.getField());
        }
        response.put("errors", errors);
        return response;
    }
    
    
}
