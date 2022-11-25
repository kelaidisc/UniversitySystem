package com.kelaidisc.config;

import com.kelaidisc.converter.course.CourseCreateDtoToCourse;
import com.kelaidisc.converter.course.CourseUpdateDtoToCourse;
import com.kelaidisc.converter.professor.ProfessorCreateDtoToProfessor;
import com.kelaidisc.converter.professor.ProfessorUpdateDtoToProfessor;
import com.kelaidisc.converter.student.StudentCreateDtoToStudent;
import com.kelaidisc.converter.student.StudentUpdateDtoToStudent;
import com.kelaidisc.mappers.CourseMapper;
import com.kelaidisc.mappers.ProfessorMapper;
import com.kelaidisc.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final CourseMapper courseMapper;
  private final ProfessorMapper professorMapper;
  private final StudentMapper studentMapper;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new CourseCreateDtoToCourse(courseMapper));
    registry.addConverter(new CourseUpdateDtoToCourse(courseMapper));
    registry.addConverter(new ProfessorCreateDtoToProfessor(professorMapper));
    registry.addConverter(new ProfessorUpdateDtoToProfessor(professorMapper));
    registry.addConverter(new StudentCreateDtoToStudent(studentMapper));
    registry.addConverter(new StudentUpdateDtoToStudent(studentMapper));
  }
}
