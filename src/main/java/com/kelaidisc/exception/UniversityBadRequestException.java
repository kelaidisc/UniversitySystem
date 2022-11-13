package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UniversityBadRequestException extends RuntimeException {

    protected String message;
    protected Object className;
    protected String fieldName;

    public UniversityBadRequestException(Object className, String fieldName, String message) {

        super(String.format("%s not found.%s %s", className.getClass().getName(), fieldName, message));
        this.className = className;
        this.fieldName = fieldName;
        this.message = message;

    }
}