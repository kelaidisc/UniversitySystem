package com.kelaidisc.service;


import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  /*
  TODO ok
  1. Create a new package, call it exception
  2. Create a new class called UniversityNotFoundException and extend from RuntimeException
  3. Convert the Optional<Student> coming from the CrudRepository to Student by adding the .orElseThrow(() -> new NotFoundException());
 */
  public Student findById(@NonNull Long id) {
    return studentRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException());
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
    return studentRepository.save(student);
  }

  public Student update(Student student) {
    return studentRepository.save(student);
  }

  public void deleteByIds(@NonNull Set<Long> ids) {
    studentRepository.deleteAllById(ids);
  }
}
