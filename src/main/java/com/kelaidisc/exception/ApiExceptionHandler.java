package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public ApiExceptionHandler() {
        super();
    }

    @ExceptionHandler(value = UniversityBadRequestException.class)
    public ResponseEntity<Object> handleUniversityBadRequestException(UniversityBadRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Athens"))
        );
        return new ResponseEntity<>(errorDetails, badRequest);
    }

    @ExceptionHandler(value = UniversityDuplicateResourceException.class)
    public ResponseEntity<Object> handleUniversityDuplicateResourceException(UniversityDuplicateResourceException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Athens"))
        );
        return new ResponseEntity<>(errorDetails, badRequest);
    }

    @ExceptionHandler(value = UniversityNotFoundException.class)
    public ResponseEntity<Object> handleUniversityNotFoundException(UniversityNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Europe/Athens"))
        );
        return new ResponseEntity<>(errorDetails, notFound);
    }
}
