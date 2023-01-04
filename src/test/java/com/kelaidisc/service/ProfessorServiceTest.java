package com.kelaidisc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
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
    String name = "some string";

    // when
    underTest.search(name, null, null, null);

    // then
    verify(professorRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(name, null, null, null);
  }

  @Test
  void canSearchProfessorByEmail() {

    // given
    String email = "some string";

    // when
    underTest.search(null, email, null, null);

    // then
    verify(professorRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);
  }

  @Test
  void canSearchProfessorByPhone() {

    // given
    String phone = "some string";

    // when
    underTest.search(null, null, phone, null);

    // then
    verify(professorRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);
  }

  @Test
  void canSearchProfessorByBirthday() {

    // given
    LocalDate birthday = LocalDate.of(1992, 3, 5);

    // when
    underTest.search(null, null, null, birthday);

    // then
    verify(professorRepository)
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
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
            ("Professor with email: " + professor.getEmail() + " already exists");
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
            ("Professor with phone: " + professor.getPhone() + " already exists");
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
            ("Professor with email: " + professor.getEmail() + " already exists");
  }

  @Test
  void willThrowWhenPhoneNotUniqueUpdate() {

    // given
    Professor professor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .email("fotisZ@gmail.com")
        .phone("+306999030405")
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
            ("Professor with phone: " + professor.getPhone() + " already exists");
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