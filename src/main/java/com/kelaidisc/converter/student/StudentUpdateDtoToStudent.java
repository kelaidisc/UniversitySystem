package com.kelaidisc.converter.student;

import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.student.StudentUpdateDto;
import com.kelaidisc.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentUpdateDtoToStudent implements Converter<StudentUpdateDto, Student> {

  private final StudentMapper studentMapper;

  @Override
  public Student convert(StudentUpdateDto source) {
    return studentMapper.fromUpdateDtoToStudent(source);
  }
}