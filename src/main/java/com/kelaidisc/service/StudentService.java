package com.kelaidisc.service;


import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Student;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.previousRepos.UserRepositoryMine;
import com.kelaidisc.repository.previousRepos.StudentRepositoryMine;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.NonNull;

public class StudentService {
  private final UserRepositoryMine<Student> studentRepository = new StudentRepositoryMine();

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  public Student findById(@NonNull Long id) {
    return studentRepository.findById(id);
  }

  public List<Student> search(StudentSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> (studentRepository.findAllByFirstNameLike(searchTerm));
      case LAST_NAME -> (studentRepository.findAllByLastNameLike(searchTerm));
      case EMAIL -> List.of(studentRepository.findByEmail(searchTerm));
      case PHONE -> List.of(studentRepository.findByPhone(searchTerm));
      case BIRTHDAY -> (studentRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER)));
    };
  }

  public Student create(Student student) {
    return studentRepository.create(student);
  }

  public Student update(Student student) {
    return studentRepository.update(student);
  }
  public void deleteByIds(@NonNull Set<Long> ids) {
    studentRepository.deleteByIds(ids);
  }
}
