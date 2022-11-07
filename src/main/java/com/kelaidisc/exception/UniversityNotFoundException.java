package com.kelaidisc.exception;

public class UniversityNotFoundException extends RuntimeException {

    public UniversityNotFoundException() {
    }

    public UniversityNotFoundException(String message) {
        super(message);
    }
}
