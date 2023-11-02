package com.carlos.todolistrocketseat.Exceptions.InvalidDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class InvalidDateExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDateException.class)
    public Map<String,String> handleInvalidDateException(InvalidDateException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorCode", HttpStatus.BAD_REQUEST.toString().toLowerCase());
        errorMap.put("message", e.getMessage());
        return errorMap;
    }
}
