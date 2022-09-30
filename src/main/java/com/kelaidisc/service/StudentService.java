package com.kelaidisc.service;


import com.kelaidisc.domain.Student;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import com.kelaidisc.repository.impl.MySqlStudentRepositoryImpl;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

public class StudentService {
    private final StudentRepository studentRepository = new MySqlStudentRepositoryImpl();

    public List<Student> findAll(){ return studentRepository.findAll();}

    public Student findById(@NonNull Long id){return studentRepository.findById(id);}

    public List<Student> search(StudentSearchField searchField,String searchTerm){
       return switch (searchField){
            case FIRST_NAME -> (studentRepository.findAllByFirstNameLike(searchTerm));
            case LAST_NAME -> (studentRepository.findAllByLastNameLike(searchTerm));
            case EMAIL -> List.of(studentRepository.findByEmail(searchTerm));
            case PHONE -> List.of(studentRepository.findByPhone(searchTerm));
            case BIRTHDAY -> (studentRepository.findAllByBirthday(LocalDate.parse(searchTerm,DATE_FORMATTER)));
       };
    }

    public Student create(Student student){return studentRepository.create(student);}

    public Student update(Student student){return studentRepository.update(student);}

    public void deleteByIds(@NonNull Set<Long> ids){ studentRepository.deleteByIds(ids);}
}
