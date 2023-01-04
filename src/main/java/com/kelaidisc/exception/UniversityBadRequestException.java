package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityBadRequestException extends UniversityException {

  protected String message;

  private static final String businessCode = "10001";

  private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public UniversityBadRequestException(String message) {

    super(String.format(message), HTTP_STATUS, businessCode);
    this.message = message;
  }
}