package com.kelaidisc.converter.professor;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.professor.ProfessorCreateDto;
import com.kelaidisc.mappers.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorCreateDtoToProfessor implements Converter<ProfessorCreateDto, Professor> {
  private final ProfessorMapper professorMapper;

  @Override
  public Professor convert(ProfessorCreateDto source) {
    return professorMapper.fromCreateDtoToProfessor(source);

  }
}