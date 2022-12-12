package com.kelaidisc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.ProfessorRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(FlywayTestConfig.class)
@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

  @Mock
  private ProfessorRepository professorRepository;
  private ProfessorService underTest;

  @BeforeEach
  void setUp() {
    underTest = new ProfessorService(professorRepository);
  }

  @Test
  void canFindAllProfessors() {

    // when
    underTest.findAll();

    // then
    verify(professorRepository).findAll();
  }

  @Test
  void canFindProfessorOptional() {

    // when
    underTest.find(1L);

    // then
    verify(professorRepository).findById(1L);
  }

  @Test
  void canFindProfessor() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    Optional<Professor> professorOptional = Optional.of(professor);

    // when
    Mockito.when(professorRepository.findById(1L)).thenReturn(professorOptional);
    Professor expectedProfessor = underTest.findOrThrow(1L);

    // then
    assertThat(expectedProfessor).isEqualTo(professor);
  }

  @Test
  void canSearchProfessorByFirstName() {

    // given
    ProfessorSearchField firstName = ProfessorSearchField.FIRST_NAME;
    String searchTerm = "some string";

    // when
    underTest.search(firstName, searchTerm);

    // then
    verify(professorRepository).findAllByFirstNameContainingIgnoreCase(searchTerm);
  }

  @Test
  void canSearchProfessorByLastName() {

    // given
    ProfessorSearchField lastName = ProfessorSearchField.LAST_NAME;
    String searchTerm = "some string";

    // when
    underTest.search(lastName, searchTerm);

    // then
    verify(professorRepository).findAllByLastNameContainingIgnoreCase(searchTerm);
  }

  @Test
  void canSearchProfessorByEmail() {

    // given
    ProfessorSearchField email = ProfessorSearchField.EMAIL;
    String searchTerm = "some string";

    // when
    underTest.search(email, searchTerm);

    // then
    verify(professorRepository).findByEmail(searchTerm);
  }

  @Test
  void canSearchProfessorByPhone() {

    // given
    ProfessorSearchField phone = ProfessorSearchField.PHONE;
    String searchTerm = "some string";

    // when
    underTest.search(phone, searchTerm);

    // then
    verify(professorRepository).findByPhone(searchTerm);
  }

  @Test
  void canSearchProfessorByBirthday() {

    // given
    ProfessorSearchField birthday = ProfessorSearchField.BIRTHDAY;
    String searchTerm = "1999-03-05";

    // when
    underTest.search(birthday, searchTerm);

    // then
    verify(professorRepository).findAllByBirthday(LocalDate.parse(searchTerm));
  }

  @Test
  void canCreateProfessor() {

    // given
    Professor professor = Professor.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    // when
    underTest.create(professor);

    // then

    ArgumentCaptor<Professor> professorArgumentCaptor =
        ArgumentCaptor.forClass(Professor.class);

    verify(professorRepository)
        .save(professorArgumentCaptor.capture());

    Professor capturedProfessor = professorArgumentCaptor.getValue();
    assertThat(capturedProfessor).isEqualTo(professor);
  }

  @Test
  void willThrowWhenEmailNotUniqueCreate() {

    // given
    Professor professor = Professor.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    given(professorRepository
        .existsByEmailAndIdIsNot(professor.getEmail(), professor.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.create(professor))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Professor.class + " with email: " + professor.getEmail() + " already exists");
  }

  @Test
  void willThrowWhenPhoneNotUniqueCreate() {

    // given
    Professor professor = Professor.builder()
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    given(professorRepository
        .existsByPhoneAndIdIsNot(professor.getPhone(), professor.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.create(professor))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Professor.class + " with phone: " + professor.getPhone() + " already exists");
  }

  @Test
  void canUpdateProfessor() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    // when
    underTest.update(professor);

    // then

    ArgumentCaptor<Professor> professorArgumentCaptor =
        ArgumentCaptor.forClass(Professor.class);

    verify(professorRepository)
        .save(professorArgumentCaptor.capture());

    Professor capturedProfessor = professorArgumentCaptor.getValue();
    assertThat(capturedProfessor).isEqualTo(professor);
  }

  @Test
  void willThrowWhenEmailNotUniqueUpdate() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("andym@hotmail.com")
        .phone("+306999550909")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    given(professorRepository
        .existsByEmailAndIdIsNot(professor.getEmail(), professor.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.update(professor))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Professor.class + " with email: " + professor.getEmail() + " already exists");
  }

  @Test
  void willThrowWhenPhoneNotUniqueUpdate() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+30 6999030405")
        .birthday(LocalDate.of(1993, 9, 12))
        .build();

    given(professorRepository
        .existsByPhoneAndIdIsNot(professor.getPhone(), professor.getId()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.create(professor))
        .isInstanceOf(UniversityDuplicateResourceException.class)
        .hasMessageContaining
            (Professor.class + " with phone: " + professor.getPhone() + " already exists");
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
    verify(professorRepository).deleteAllByIdIn(ids);
  }
}