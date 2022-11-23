package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityBadRequestException extends UniversityException {

  protected String message;
  protected Object className;
  protected String fieldName;

  private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public UniversityBadRequestException(Object className, String fieldName, String message) {

    super(String.format("%s not found.%s %s", className, fieldName, message), HTTP_STATUS);
    this.className = className;
    this.fieldName = fieldName;
    this.message = message;

  }
}
