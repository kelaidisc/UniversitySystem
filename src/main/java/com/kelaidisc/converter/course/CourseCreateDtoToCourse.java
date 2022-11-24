package com.kelaidisc.converter.course;

import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.mappers.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCreateDtoToCourse implements Converter<CourseCreateDto, Course> {

  private final CourseMapper courseMapper;

  @Override
  public Course convert(CourseCreateDto source) {
    return courseMapper.fromCreateDtoToCourse(source);
  }
}