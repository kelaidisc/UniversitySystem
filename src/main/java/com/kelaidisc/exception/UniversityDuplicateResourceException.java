package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UniversityDuplicateResourceException extends RuntimeException {
    protected Object className;
    protected String fieldName;

    public UniversityDuplicateResourceException(Object className, String fieldName) {

        super(String.format("%s: %s already exists", className.getClass().getName(), fieldName));
        this.className = className;
        this.fieldName = fieldName;
    }

}
