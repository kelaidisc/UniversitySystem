package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;

public class UniversityNotFoundException extends UniversityException {

  protected Object className;
  protected long id;
  private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

  public UniversityNotFoundException(Object className, long courseId) {
    super(String.format("id: %d, doesn't exist for %s", courseId, className), HTTP_STATUS);
    this.className = className;
    this.id = courseId;

  }

}