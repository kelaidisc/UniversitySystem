package com.kelaidisc.converter.professor;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.professor.ProfessorCreateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfessorCreateDtoToProfessor implements Converter<ProfessorCreateDto, Professor> {

    @Override
    public Professor convert(ProfessorCreateDto source) {
        return Professor.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phone(source.getPhone())
                .birthday(source.getBirthday())
                .build();

    }
}
