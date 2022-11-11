package com.kelaidisc.converter.course;

import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.course.CourseUpdateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CourseUpdateDtoToCourse implements Converter<CourseUpdateDto, Course> {

  @Override
  public Course convert(CourseUpdateDto source) {
    return Course.builder()
        .id(source.getId())
        .name(source.getName())
        .description(source.getDescription())
        .build();
  }
}
