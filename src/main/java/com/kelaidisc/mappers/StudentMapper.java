package com.kelaidisc.mappers;

import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.student.StudentCreateDto;
import com.kelaidisc.dto.student.StudentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(target = "courses", ignore = true)
  Student fromCreateDtoToStudent(StudentCreateDto studentCreateDto);

  @Mapping(target = "courses", ignore = true)
  Student fromUpdateDtoToStudent(StudentUpdateDto studentUpdateDto);
}