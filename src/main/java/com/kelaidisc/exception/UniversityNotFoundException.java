package com.kelaidisc.exception;

import com.kelaidisc.domain.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniversityNotFoundException extends RuntimeException {

  protected Class<Course> courseClass;
  protected long id;

  public UniversityNotFoundException(Class<Course> courseClass, long courseId) {

    this.courseClass = courseClass;
    this.id = courseId;

  }
}
