package com.kelaidisc.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Student;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
class StudentRepositoryTest {

  @Autowired
  private StudentRepository underTest;

  @Test
  void shouldFindAllByFirstName() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String firstName = "thomas";

    // when
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(firstName, null, null, null);
    // after returning the list,the comparison in firstName's value is being done in lowercase
    List<String> firstNames = students.stream()
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
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(firstName, null, null, null);
    List<String> firstNames = students.stream()
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
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String lastName = "kefalas";

    // when
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(lastName, null, null, null);
    // after returning the list,the comparison in lastName's value is being done in lowercase
    List<String> lastNames = students.stream()
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
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(lastName, null, null, null);
    List<String> lastNames = students.stream()
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
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    LocalDate birthday = LocalDate.of(1992, 3, 4);

    // when
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
    List<LocalDate> birthdays = students.stream()
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
    List<Student> students = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, null, birthday);
    List<LocalDate> birthdays = students.stream()
        .flatMap(s -> Stream.of(s.getBirthday()))
        .toList();

    // then
    assertThat(birthdays.isEmpty()).isTrue();
  }

  @Test
  void shouldFindStudentByEmail() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String email = "kefthom@gmail.com";

    // when
    List<Student> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);

    // then
    assertThat(expected.size()).isEqualTo(1);
  }

  @Test
  void shouldNotFindStudentByEmail() {

    // given
    String email = "anEmailThatDoesNotExist";

    // when
    List<Student> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, email, null, null);

    // then
    assertThat(expected.isEmpty()).isTrue();
  }

  @Test
  void shouldFindStudentByPhone() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String phone = "+306994545556";

    List<Student> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);

    // then
    assertThat(expected.size()).isEqualTo(1);
  }

  @Test
  void shouldNotFindStudentByPhone() {

    // given
    String phone = "aPhoneThatDoesNotExist";

    List<Student> expected = underTest
        .findAllByNameOrEmailOrPhoneOrBirthday(null, null, phone, null);

    // then
    assertThat(expected.isEmpty()).isTrue();
  }

  @Test
  void shouldDeleteAllByIdIn() {

    // given
    // TestData has 5 students
    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    // when
    underTest.deleteAllByIdIn(ids);
    List<Student> allStudents = underTest.findAll();

    // then
    assertThat(allStudents.size()).isEqualTo(3);
  }

  @Test
  void shouldNotDeleteAllByIdIn() {

    // given
    // TestData has 5 students
    Set<Long> ids = new HashSet<>();
    ids.add(10L);
    ids.add(11L);

    // when
    underTest.deleteAllByIdIn(ids);
    List<Student> allStudents = underTest.findAll();

    // then
    assertThat(allStudents.size()).isEqualTo(5);
  }

  @Test
  void shouldExistByEmailAndIdNot() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String email = "kefthom@gmail.com";
    Long id = 10L;

    // when
    boolean expected = underTest.existsByEmailAndIdIsNot(email, id);

    // then
    assertThat(expected).isTrue();
  }

  @Test
  void shouldNotExistByEmailAndIdNot() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String email = "kefthom@gmail.com";
    Long id = 1L;

    // when
    boolean expected = underTest.existsByEmailAndIdIsNot(email, id);

    // then
    assertThat(expected).isFalse();
  }

  @Test
  void shouldExistByPhoneAndIdNot() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String phone = "+306994545556";
    Long id = 10L;

    // when
    boolean expected = underTest.existsByPhoneAndIdIsNot(phone, id);

    // then
    assertThat(expected).isTrue();
  }

  @Test
  void shouldNotExistByPhoneAndIdNot() {

    // given
    /* TestData
     * id: 1
     * firstName: Thomas
     * lastName: Kefalas
     * email: kefthom@gmail.com
     * phone: +306994545556
     * birthday: 1992-03-04
     * registrationDate: 2021-03-04 */
    String phone = "+306994545556";
    Long id = 1L;

    // when
    boolean expected = underTest.existsByEmailAndIdIsNot(phone, id);

    // then
    assertThat(expected).isFalse();
  }

  @Test
  void shouldFindAllByIdIn() {

    // given
    // TestData has 5 students
    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    // when
    Set<Student> expected = underTest.findAllByIdIn(ids);

    // then
    assertThat(expected.size()).isEqualTo(2);
  }

  @Test
  void shouldNotFindAnyByIdIn() {

    // given
    // TestData has 5 students
    Set<Long> ids = new HashSet<>();
    ids.add(10L);
    ids.add(11L);

    // when
    Set<Student> expected = underTest.findAllByIdIn(ids);

    // then
    assertThat(expected.size()).isEqualTo(0);
  }
}