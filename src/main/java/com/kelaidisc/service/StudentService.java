package com.kelaidisc.service;


import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  public Optional<Student> find(Long id) {
    return studentRepository.findById(id);
  }

  @Transactional
  public Student findOrThrow(Long id) {
    return studentRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Student.class, id));
  }

  public List<Student> search(StudentSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> (studentRepository.findAllByFirstNameEqualsIgnoreCaseLike(searchTerm));
      case LAST_NAME -> (studentRepository.findAllByLastNameEqualsIgnoreCaseLike(searchTerm));
      case EMAIL -> Stream.of(studentRepository.findByEmail(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case PHONE -> Stream.of(studentRepository.findByPhone(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case BIRTHDAY -> (studentRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER)));
    };
  }

  public Student create(Student student) {

    if (studentRepository.existsByLastNameAndFirstName(student.getLastName(), student.getFirstName())) {
      throw new UniversityDuplicateResourceException
          (Student.class, "name", student.getLastName() + " " + student.getFirstName());
    }
    return studentRepository.save(student);

  }

  public Student update(Student student) {

    //TODO this test doesn't make sense
    if (studentRepository.existsByLastNameAndFirstName(student.getLastName(), student.getFirstName())) {
      throw new UniversityDuplicateResourceException
          (Student.class, "name", student.getLastName() + " " + student.getFirstName());
    }
    return studentRepository.save(student);

  }

  public void deleteAllByIdIn(Set<Long> ids) {
    studentRepository.deleteAllByIdIn(ids);
  }
}
