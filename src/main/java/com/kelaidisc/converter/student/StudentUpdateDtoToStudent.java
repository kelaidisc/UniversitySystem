package com.kelaidisc.converter.student;

import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.student.StudentUpdateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentUpdateDtoToStudent implements Converter<StudentUpdateDto, Student> {

  @Override
  public Student convert(StudentUpdateDto source) {
    return Student.builder()
        .id(source.getId())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .email(source.getEmail())
        .phone(source.getPhone())
        .birthday(source.getBirthday())
        .registrationDate(source.getRegistrationDate())
        .build();
  }
}
