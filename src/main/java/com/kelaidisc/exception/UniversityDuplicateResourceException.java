package com.kelaidisc.exception;

import com.kelaidisc.domain.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UniversityDuplicateResourceException extends RuntimeException {

    protected Class<Course> courseClass;
    protected String fieldName;

    public UniversityDuplicateResourceException(Class<Course> courseClass, String fieldName) {
        this.courseClass = courseClass;
        this.fieldName = fieldName;
    }

}
