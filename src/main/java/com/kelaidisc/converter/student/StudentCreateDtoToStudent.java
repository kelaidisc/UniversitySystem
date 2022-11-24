package com.kelaidisc.converter.student;

import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.student.StudentCreateDto;
import com.kelaidisc.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentCreateDtoToStudent implements Converter<StudentCreateDto, Student> {
  private final StudentMapper studentMapper;

  @Override
  public Student convert(StudentCreateDto source) {
    return studentMapper.fromCreateDtoToStudent(source);
  }
}