package com.kelaidisc.converter.professor;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.professor.ProfessorUpdateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class ProfessorUpdateDtoToProfessor implements Converter<ProfessorUpdateDto, Professor> {
    @Override
    public Professor convert(ProfessorUpdateDto source) {
        return Professor.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phone(source.getPhone())
                .birthday(source.getBirthday())
                .build();
    }
}
