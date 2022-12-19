package com.kelaidisc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;
  private StudentService underTest;

  @BeforeEach
  void setUp() {
    underTest = new StudentService(studentRepository);
  }

  @Test
  void canFindAllStudents() {

    // when
    underTest.findAll();

    // then
    verify(studentRepository).findAll();
  }

  @Test
  void canFindStudentOptional() {

    // when
    underTest.find(1L);

    // then
    verify(studentRepository).findById(1L);
  }

  @Test
  void canFindStudent() {

    // given
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    Optional<Student> studentOptional = Optional.of(student);

    // when
    Mockito.when(studentRepository.findById(1L)).thenReturn(studentOptional);
    Student expectedStudent = underTest.findOrThrow(1L);

    // then
    assertThat(expectedStudent).isEqualTo(student);
  }

  @Test
  void canSearchStudentByFirstName() {

    // given
    StudentSearchField firstName = StudentSearchField.FIRST_NAME;
    String searchTerm = "some string";

    // when
    underTest.search(firstName, searchTerm);

    // then
    verify(studentRepository).findAllByFirstNameContainingIgnoreCase(searchTerm);
  }

  @Test
  void canSearchStudentByLastName() {

    // given
    StudentSearchField lastName = StudentSearchField.LAST_NAME;
    String searchTerm = "some string";

    // when
    underTest.search(lastName, searchTerm);

    // then
    verify(studentRepository).findAllByLastNameContainingIgnoreCase(searchTerm);
  }

  @Test
  void canSearchStudentByEmail() {

    // given
    StudentSearchField email = StudentSearchField.EMAIL;
    String searchTerm = "some string";

    // when
    underTest.search(email, searchTerm);

    // then
    verify(studentRepository).findByEmail(searchTerm);
  }

  @Test
  void canSearchStudentByPhone() {

    // given
    StudentSearchField phone = StudentSearchField.PHONE;
    String searchTerm = "some string";

    // when
    underTest.search(phone, searchTerm);

    // then
    verify(studentRepository).findByPhone(searchTerm);
  }

  @Test
  void canSearchStudentByBirthday() {

    // given
    StudentSearchField birthday = StudentSearchField.BIRTHDAY;
    String searchTerm = "1999-03-05";

    // when
    underTest.search(birthday, searchTerm);

    // then
    verify(studentRepository).findAllByBirthday(LocalDate.parse(searchTerm));
  }

  @Test
  void canCreateStudent() {

    // given
    Student student = Student.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    // when
    underTest.create(student);

    // then

    ArgumentCaptor<Student> studentArgumentCaptor =
        ArgumentCaptor.forClass(Student.class);

    verify(studentRepository)
        .save(studentArgumentCaptor.capture());

    Student capturedStudent = studentArgumentCaptor.getValue();
    assertThat(capturedStudent).isEqualTo(student);
  }

  @Test
  void willThrowWhenEmailNotUniqueCreate() {

    // given
    Student student = Student.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    given(studentRepository
        .existsByEmailAndIdIsNot(student.getEmail(), student.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.create(student))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Student.class + " with email: " + student.getEmail() + " already exists");
  }

  @Test
  void willThrowWhenPhoneNotUniqueCreate() {

    // given
    Student student = Student.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    given(studentRepository
        .existsByPhoneAndIdIsNot(student.getPhone(), student.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.create(student))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Student.class + " with phone: " + student.getPhone() + " already exists");
  }

  @Test
  void canUpdateStudent() {

    // given
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    // when
    underTest.update(student);

    // then

    ArgumentCaptor<Student> studentArgumentCaptor =
        ArgumentCaptor.forClass(Student.class);

    verify(studentRepository)
        .save(studentArgumentCaptor.capture());

    Student capturedStudent = studentArgumentCaptor.getValue();
    assertThat(capturedStudent).isEqualTo(student);
  }

  @Test
  void willThrowWhenEmailNotUniqueUpdate() {

    // given
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("alexkel12@hotmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    given(studentRepository
        .existsByEmailAndIdIsNot(student.getEmail(), student.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.update(student))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Student.class + " with email: " + student.getEmail() + " already exists");
  }

  @Test
  void willThrowWhenPhoneNotUniqueUpdate() {

    // given
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+30 6922324252")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 11, 15))
        .build();

    given(studentRepository
        .existsByPhoneAndIdIsNot(student.getPhone(), student.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.update(student))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Student.class + " with phone: " + student.getPhone() + " already exists");
  }

  @Test
  void canDeleteAllByIdIn() {

    // given
    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    // when
    underTest.deleteAllByIdIn(ids);

    // then
    verify(studentRepository).deleteAllByIdIn(ids);
  }
}
