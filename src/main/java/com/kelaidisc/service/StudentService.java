package com.kelaidisc.service;


import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  public Optional<Student> find(Long id){
    return studentRepository.findById(id);
  }

  public Student findOrThrow(Long id) {
    return studentRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Student.class, id));
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

    if(studentRepository.existsByLastNameAndFirstName(student.getLastName(), student.getFirstName())) {
      throw new UniversityDuplicateResourceException
              (Student.class, "name", student.getLastName() + " " + student.getFirstName());
    }
    return studentRepository.save(student);

  }

  public Student update(Student student) {

    if(studentRepository.existsByLastNameAndFirstName(student.getLastName(), student.getFirstName())) {
      throw new UniversityDuplicateResourceException
              (Student.class, "name", student.getLastName() + " " + student.getFirstName());
    }
    return studentRepository.save(student);

  }

  public void deleteByIds(Set<Long> ids) {
    studentRepository.deleteAllById(ids);
  }
}
