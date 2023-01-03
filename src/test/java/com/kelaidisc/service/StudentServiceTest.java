package com.kelaidisc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
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
    String name = "some string";

    // when
    underTest.search(name, null, null, null);

    // then
    verify(studentRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(name, null, null, null);
  }

  @Test
  void canSearchStudentByEmail() {

    // given
    String email = "some string";

    // when
    underTest.search(null, email, null, null);

    // then
    verify(studentRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);
  }

  @Test
  void canSearchStudentByPhone() {

    // given
    String phone = "some string";

    // when
    underTest.search(null, null, phone, null);

    // then
    verify(studentRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);
  }

  @Test
  void canSearchStudentByBirthday() {

    // given
    LocalDate birthday = LocalDate.of(1992, 3, 5);

    // when
    underTest.search(null, null, null, birthday);

    // then
    verify(studentRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
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
        .phone("+306922324252")
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