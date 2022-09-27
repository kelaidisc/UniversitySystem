package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface StudentRepository {

    List<Student> findAll();

    List<Student> findAllByFirstNameLike(String firstName);

    List<Student> findAllByLastNameLike(String lastName);

    List<Student> findAllByBirthday(LocalDate birthday);

    Student findById(Long id);

    Student findByEmail(String email);

    Student findByPhone(String phone);

    Student create(Student student);

    Student update(Student student);

    void deleteByIds(Set<Long> ids);
}
