package com.kelaidisc.converter.professor;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.professor.ProfessorUpdateDto;
import com.kelaidisc.mappers.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProfessorUpdateDtoToProfessor implements Converter<ProfessorUpdateDto, Professor> {
  private final ProfessorMapper professorMapper;

  @Override
  public Professor convert(ProfessorUpdateDto source) {
    return professorMapper.fromUpdateDtoToProfessor(source);
  }
}