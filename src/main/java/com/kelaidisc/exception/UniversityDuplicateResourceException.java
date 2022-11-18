package com.kelaidisc.exception;

public class UniversityDuplicateResourceException extends RuntimeException {
    protected Object className;
    protected String name;
    protected String fieldValue;

    public UniversityDuplicateResourceException(Object className, String name, String fieldValue) {

        super(String.format("%s with %s: %s already exists", className.getClass().getName(), name, fieldValue));
        this.className = className;
        this.name = name;
        this.fieldValue = fieldValue;
    }

}
