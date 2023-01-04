package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityDuplicateResourceException extends UniversityException {
  protected Object className;
  protected String name;
  protected String fieldValue;

  private static final String businessCode = "10002";
  private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public UniversityDuplicateResourceException(Class<?> className, String name, String fieldValue) {

    super(String.format("%s with %s: %s already exists",
        className.getSimpleName(), name, fieldValue), HTTP_STATUS, businessCode);
    this.className = className;
    this.name = name;
    this.fieldValue = fieldValue;
  }
}