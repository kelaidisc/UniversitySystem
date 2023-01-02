package com.kelaidisc.exception;

import java.security.InvalidParameterException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @ExceptionHandler ({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler ({InvalidParameterException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleInvalidParameterException(
      InvalidParameterException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
