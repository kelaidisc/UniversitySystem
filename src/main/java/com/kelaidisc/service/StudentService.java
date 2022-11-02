package com.kelaidisc.service;


import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Student;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.UserRepository;
import com.kelaidisc.repository.impl.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.NonNull;

public class StudentService {
  private final UserRepository<Student> studentRepository = new StudentRepository();

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

  // TODO Now that Courses are linked with Students, in order to delete a Student you must first delete the related rows from the course_students table ok
  // TODO This operation needs to be transactional. Why? What does it mean? (Google it and ask if any questions) and try to create a transaction ok
  public void deleteByIds(@NonNull Set<Long> ids) {
    studentRepository.deleteByIds(ids);
  }
}
