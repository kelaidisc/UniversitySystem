package com.kelaidisc.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Professor;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Import(FlywayTestConfig.class)
public class ProfessorRepositoryTest {

  @Autowired
  private ProfessorRepository underTest;

  @Test
  void shouldFindAllByFirstName() {

    // given
    /* TestData
     * id: 1
     * firstName: John
     * lastName: Giannopoulos
     * email: johnnyg@gmail.com
     * phone: +306976770000
     * birthday: 1984-07-08 */
    String firstName = "john";

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(firstName, null, null, null);
    // after returning the list,the comparison in firstName's value is being done in lowercase
    List<String> firstNames = professors.stream()
        .flatMap(s -> Stream.of(s.getFirstName()))
        .map(String::toLowerCase)
        .toList();

    // then
    assertThat(firstName.toLowerCase()).isIn(firstNames);
  }

  @Test
  void shouldNotFindAnyByFirstName() {

    // given
    String firstName = "aNameThatDoesNotExist";

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(firstName, null, null, null);
    List<String> firstNames = professors.stream()
        .flatMap(s -> Stream.of(s.getFirstName()))
        .toList();

    // then
    assertThat(firstNames.isEmpty()).isTrue();
  }

  @Test
  void shouldFindAllByLastName() {

    // given
    /* TestData
     * id: 1
     * firstName: John
     * lastName: Giannopoulos
     * email: johnnyg@gmail.com
     * phone: +306976770000
     * birthday: 1984-07-08 */
    String lastName = "giannopoulos";

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(lastName, null, null, null);
    // after returning the list,the comparison in lastName's value is being done in lowercase
    List<String> lastNames = professors.stream()
        .flatMap(s -> Stream.of(s.getLastName()))
        .map(String::toLowerCase)
        .toList();

    // then
    assertThat(lastName.toLowerCase()).isIn(lastNames);
  }

  @Test
  void shouldNotFindAnyByLastName() {

    // given
    String lastName = "aNameThatDoesNotExist";

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(lastName, null, null, null);
    List<String> lastNames = professors.stream()
        .flatMap(s -> Stream.of(s.getLastName()))
        .toList();

    // then
    assertThat(lastNames.isEmpty()).isTrue();
  }

  @Test
  void shouldFindAllByBirthday() {

    // given
    /* TestData
     * id: 1
     * firstName: John
     * lastName: Giannopoulos
     * email: johnnyg@gmail.com
     * phone: +306976770000
     * birthday: 1984-07-08 */
    LocalDate birthday = LocalDate.of(1984, 7, 8);

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
    List<LocalDate> birthdays = professors.stream()
        .flatMap(s -> Stream.of(s.getBirthday()))
        .toList();

    // then
    assertThat(birthday).isIn(birthdays);
  }

  @Test
  void shouldNotFindAnyByBirthday() {

    // given
    LocalDate birthday = LocalDate.of(992, 3, 4);

    // when
    List<Professor> professors = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
    List<LocalDate> birthdays = professors.stream()
        .flatMap(s -> Stream.of(s.getBirthday()))
        .toList();

    // then
    assertThat(birthdays.isEmpty()).isTrue();
  }

  @Test
  void shouldFindProfessorByEmail() {

    // given
    /* TestData
     * id: 1
     * firstName: John
     * lastName: Giannopoulos
     * email: johnnyg@gmail.com
     * phone: +306976770000
     * birthday: 1984-07-08 */
    String email = "johnnyg@gmail.com";

    // when
    List<Professor> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);

    // then
    assertThat(expected.size()).isEqualTo(1);
  }

  @Test
  void shouldNotFindProfessorByEmail() {

    // given
    String email = "anEmailThatDoesNotExist";

    // when
    List<Professor> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);

    // then
    assertThat(expected.isEmpty()).isTrue();
  }

  @Test
  void shouldFindProfessorByPhone() {

    // given
    /* TestData
     * id: 1
     * firstName: John
     * lastName: Giannopoulos
     * email: johnnyg@gmail.com
     * phone: +306976770000
     * birthday: 1984-07-08 */
    String phone = "+306976770000";

    List<Professor> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);

    // then
    assertThat(expected.size()).isEqualTo(1);
  }

  @Test
  void shouldNotFindProfessorByPhone() {

    // given
    String phone = "aPhoneThatDoesNotExist";

    List<Professor> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);

    // then
    assertThat(expected.isEmpty()).isTrue();
  }
}