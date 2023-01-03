package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityDuplicateResourceException extends UniversityException {
  protected Object className;
  protected String name;
  protected String fieldValue;
  private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public UniversityDuplicateResourceException(Object className, String name, String fieldValue) {

    super(String.format("%s with %s: %s already exists", className, name, fieldValue), HTTP_STATUS);
    this.className = className;
    this.name = name;
    this.fieldValue = fieldValue;
  }

}