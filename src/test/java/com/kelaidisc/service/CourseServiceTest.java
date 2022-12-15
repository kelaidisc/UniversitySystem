package com.kelaidisc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.repository.CourseRepository;
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
class CourseServiceTest {

  @Mock
  private CourseRepository courseRepository;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private ProfessorService professorService;
  private CourseService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CourseService(courseRepository, studentRepository, professorService);
  }

  @Test
  void canFindAllCourses() {

    // when
    underTest.findAll();

    // then
    verify(courseRepository).findAll();
  }

  @Test
  void canFindCourse() {

    // given
    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .build();

    Optional<Course> courseOptional = Optional.of(course);

    // when
    Mockito.when(courseRepository.findById(1L)).thenReturn(courseOptional);
    Course expectedCourse = underTest.findOrThrow(1L);

    // then
    assertThat(expectedCourse).isEqualTo(course);
  }

  @Test
  void canCreateCourse() {

    // given
    Course course = Course.builder()
        .name("Statistics")
        .description("Text")
        .build();

    // when
    underTest.create(course);

    // then
    ArgumentCaptor<Course> courseArgumentCaptor =
        ArgumentCaptor.forClass(Course.class);

    verify(courseRepository)
        .save(courseArgumentCaptor.capture());

    Course capturedCourse = courseArgumentCaptor.getValue();
    assertThat(capturedCourse).isEqualTo(course);
  }

  @Test
  void willThrowWhenNameIneligibleCreate() {

    // given
    Course course = Course.builder()
        .name("Statistics")
        .description("Text")
        .build();

    given(courseRepository
        .existsByNameAndIdIsNot(course.getName(), course.getId()))
        .willReturn(true);

    // when
    // then

    assertThatThrownBy(() -> underTest.create(course))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Course.class + " with name: " + course.getName() + " already exists");
  }

  @Test
  void canUpdateCourse() {

    // given
    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .build();

    // when
    underTest.update(course);

    // then
    ArgumentCaptor<Course> courseArgumentCaptor =
        ArgumentCaptor.forClass(Course.class);

    verify(courseRepository)
        .save(courseArgumentCaptor.capture());

    Course capturedCourse = courseArgumentCaptor.getValue();
    assertThat(capturedCourse).isEqualTo(course);
  }

  @Test
  void willThrowWhenNameIneligibleUpdate() {

    // given
    Course course = Course.builder()
        .id(1L)
        .name("Sociology" +
            "")
        .description("Text")
        .build();

    given(courseRepository
        .existsByNameAndIdIsNot(course.getName(), course.getId()))
        .willReturn(true);

    // when
    // then

    assertThatThrownBy(() -> underTest.update(course))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Course.class + " with name: " + course.getName() + " already exists");
  }

  @Test
  void canDeleteByIds() {

    // given
    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    // when
    underTest.deleteByIds(ids);

    // then
    verify(courseRepository).deleteAllByIdIn(ids);
  }

  @Test
  void canFindCoursesByName() {

    // given
    String name = "some name";

    // when
    underTest.findAllByNameLike(name);

    // then
    verify(courseRepository).findAllByNameContainingIgnoreCase(name);
  }

  @Test
  void canAssignProfessorToCourse() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .professor(null)
        .build();

    Optional<Course> optionalCourse = Optional.of(course);

    // when
    Mockito.when(courseRepository.findById(1L)).thenReturn(optionalCourse);
    Mockito.when(professorService.findOrThrow(1L)).thenReturn(professor);
    underTest.assignProfessorToCourse(1L, 1L);

    // then
    assertThat(course.getProfessor()).isEqualTo(professor);
  }

  @Test
  void canRemoveProfessorFromCourse() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .professor(professor)
        .build();

    Optional<Course> optionalCourse = Optional.of(course);

    // when
    Mockito.when(courseRepository.findById(1L)).thenReturn(optionalCourse);
    underTest.removeProfessorFromCourse(1L);

    // then
    assertThat(course.getProfessor()).isNull();
  }

  @Test
  void enrollStudents() {

    // given
    Set<Student> students = new HashSet<>();
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 5, 5))
        .build();


    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .students(students)
        .build();

    Optional<Course> optionalCourse = Optional.of(course);

    // when
    Set<Long> ids = new HashSet<>();
    ids.add(1L);

    students.add(student);

    Mockito.when(courseRepository.findById(1L)).thenReturn(optionalCourse);
    Mockito.when(studentRepository.findAllByIdIn(ids)).thenReturn(students);

    underTest.enrollStudents(1L, ids);

    // then
    assertThat(student).isIn(course.getStudents());
  }

  @Test
  void disEnrollStudents() {

    // given
    Set<Student> students = new HashSet<>();
    Student student = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .registrationDate(LocalDate.of(2020, 5, 5))
        .build();

    students.add(student);

    Course course = Course.builder()
        .id(1L)
        .name("Statistics")
        .description("Text")
        .students(students)
        .build();

    Optional<Course> optionalCourse = Optional.of(course);

    // when
    Set<Long> ids = new HashSet<>();
    ids.add(1L);

    Mockito.when(courseRepository.findById(1L)).thenReturn(optionalCourse);
    Mockito.when(studentRepository.findAllByIdIn(ids)).thenReturn(students);

    underTest.disEnrollStudents(1L, ids);

    // then
    assertThat(student).isNotIn(course.getStudents());
  }
}