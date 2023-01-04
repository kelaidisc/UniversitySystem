package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityNotFoundException extends UniversityException {

  protected Object className;
  protected long id;

  private static final String businessCode = "10003";
  private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

  public UniversityNotFoundException(Class<?> className, long courseId) {

    super(String.format("Id: %d, doesn't exist for %s",
        courseId, className.getSimpleName()), HTTP_STATUS, businessCode);
    this.className = className;
    this.id = courseId;
  }
}