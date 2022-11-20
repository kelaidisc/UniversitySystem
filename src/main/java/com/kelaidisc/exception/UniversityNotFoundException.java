package com.kelaidisc.exception;

public class UniversityNotFoundException extends RuntimeException {

  protected Object className;
  protected long id;

  public UniversityNotFoundException(Object className, long courseId) {
    super(String.format("id: %d, doesn't exist for %s", courseId, className));
    this.className = className;
    this.id = courseId;

  }

}
