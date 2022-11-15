package com.kelaidisc.converter.student;

import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.student.StudentCreateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentCreateDtoToStudent implements Converter<StudentCreateDto, Student> {

    @Override
    public Student convert(StudentCreateDto source) {
        return Student.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phone(source.getPhone())
                .birthday(source.getBirthday())
                .registrationDate(source.getRegistrationDate())
                .build();
    }
}
