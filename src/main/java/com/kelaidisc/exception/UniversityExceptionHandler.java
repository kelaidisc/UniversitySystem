package com.kelaidisc.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UniversityExceptionHandler extends ResponseEntityExceptionHandler {

  public UniversityExceptionHandler() {
    super();
  }

  @ExceptionHandler(value = UniversityException.class)
  public ResponseEntity<Object> handleUniversityException(UniversityException e) {
    HttpStatus httpStatus = e.getHttpStatus();

    ErrorDetails errorDetails = new ErrorDetails(
        e.getMessage(),
        httpStatus,
        ZonedDateTime.now(ZoneId.of("Europe/Athens"))
    );
    return new ResponseEntity<>(errorDetails, httpStatus);
  }
}
