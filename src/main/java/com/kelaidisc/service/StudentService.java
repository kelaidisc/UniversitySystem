package com.kelaidisc.service;


import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  public Student findOrThrow(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new UniversityNotFoundException(Student.class, id));
  }

  public List<Student> search(String name, String email, String phone, LocalDate birthday) {
    return studentRepository.findAllByNameOrEmailOrPhoneOrBirthday(name, email, phone, birthday);
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
    if (studentRepository.existsByEmailAndIdIsNot(student.getEmail(), student.getId())) {
      throw new UniversityDuplicateResourceException(Student.class, "email", student.getEmail());
    }
  }

  private void validatePhone(@NotNull Student student) {
    if (studentRepository.existsByPhoneAndIdIsNot(student.getPhone(), student.getId())) {
      throw new UniversityDuplicateResourceException(Student.class, "phone", student.getPhone());
    }
  }

  public void deleteAllByIdIn(Set<Long> ids) {
    studentRepository.deleteAllByIdIn(ids);
  }
}
