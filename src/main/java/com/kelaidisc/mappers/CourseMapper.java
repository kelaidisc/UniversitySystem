package com.kelaidisc.mappers;

import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.dto.course.CourseUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
  @Mapping(target = "professor", ignore = true)
  @Mapping(target = "students", ignore = true)
  Course fromCreateDtoToCourse(CourseCreateDto courseCreateDto);

  @Mapping(target = "professor", ignore = true)
  @Mapping(target = "students", ignore = true)
  Course fromUpdateDtoToCourse(CourseUpdateDto courseUpdateDtoToCourse);
}
