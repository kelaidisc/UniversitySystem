package com.kelaidisc.mappers;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.professor.ProfessorCreateDto;
import com.kelaidisc.dto.professor.ProfessorUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProfessorMapper {
  @Mapping(target = "courses", ignore = true)
  Professor fromCreateDtoToProfessor(ProfessorCreateDto professorCreateDto);

  @Mapping(target = "courses", ignore = true)
  Professor fromUpdateDtoToProfessor(ProfessorUpdateDto professorUpdateDto);
}
