package com.kelaidisc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UniversityNotFoundException extends RuntimeException {

  protected Object className;
  protected long id;

  public UniversityNotFoundException(Object className, long courseId) {
    super(String.format("There is no %s with id : %d", className.getClass().getName(), courseId));
    this.className = className;
    this.id = courseId;

  }

}
