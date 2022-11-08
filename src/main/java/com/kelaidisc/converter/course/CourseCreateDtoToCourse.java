package com.kelaidisc.converter.course;

import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.course.CourseCreateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CourseCreateDtoToCourse implements Converter<CourseCreateDto, Course> {

  @Override
  public Course convert(CourseCreateDto source) {
    return Course.builder()
        .name(source.getName())
        .description(source.getDescription())
        .build();
  }
}
