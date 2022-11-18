package com.kelaidisc.exception;

public class UniversityNotFoundException extends RuntimeException {

  protected Object className;
  protected long id;

  public UniversityNotFoundException(Object className, long courseId) {
    super(String.format("There is no %s with id : %d", className.getClass().getName(), courseId));
    this.className = className;
    this.id = courseId;

  }

}
