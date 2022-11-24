package com.kelaidisc.converter.course;

import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.course.CourseUpdateDto;
import com.kelaidisc.mappers.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseUpdateDtoToCourse implements Converter<CourseUpdateDto, Course> {
  private final CourseMapper courseMapper;

  @Override
  public Course convert(CourseUpdateDto source) {
    return courseMapper.fromUpdateDtoToCourse(source);
  }
}