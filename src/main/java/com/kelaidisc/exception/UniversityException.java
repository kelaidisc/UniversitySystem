package com.kelaidisc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UniversityException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final String businessCode;

  public UniversityException(String message, HttpStatus httpStatus, String businessCode) {
    super(message);
    this.httpStatus = httpStatus;
    this.businessCode = businessCode;
  }
}