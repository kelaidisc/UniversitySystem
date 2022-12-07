package com.kelaidisc.service;


import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

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

  public Student findOrThrow(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new UniversityNotFoundException(Student.class, id));
  }

  public List<Student> search(StudentSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> (studentRepository.findAllByFirstNameContainingIgnoreCase(searchTerm));
      case LAST_NAME -> (studentRepository.findAllByLastNameContainingIgnoreCase(searchTerm));
      case EMAIL ->
          Stream.of(studentRepository.findByEmail(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case PHONE ->
          Stream.of(studentRepository.findByPhone(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case BIRTHDAY -> (studentRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER)));
    };
  }

  public Student create(Student student) {
    validateEmail(student);
    validatePhone(student);
    return studentRepository.save(student);
  }

  public Student update(Student student) {
    validateEmail(student);
    validatePhone(student);
    return studentRepository.save(student);
  }

  private void validateEmail(@NotNull Student student) {
    if (student.getId() == null
        && studentRepository.existsByEmailAndIdIsNot(student.getEmail(), student.getId())) {
      throw new UniversityDuplicateResourceException(Student.class, "email", student.getEmail());
    }
  }

  private void validatePhone(@NotNull Student student) {
    if (student.getId() == null
        && studentRepository.existsByPhoneAndIdIsNot(student.getPhone(), student.getId())) {
      throw new UniversityDuplicateResourceException(Student.class, "phone", student.getPhone());
    }
  }

  public void deleteAllByIdIn(Set<Long> ids) {
    studentRepository.deleteAllByIdIn(ids);
  }
}
