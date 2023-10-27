package com.carlos.todolistrocketseat.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserNotFoundExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String,String> handleUserNotFoundException(UserNotFoundException e)
    {
        Map<String, String>errorMap = new HashMap<>();
        errorMap.put("error code", HttpStatus.NOT_FOUND.toString().toLowerCase());
        errorMap.put("message", e.getMessage());
        return errorMap;
    }
}
