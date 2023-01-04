package com.kelaidisc.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Course;
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
class CourseRepositoryTest {

  @Autowired
  private CourseRepository underTest;

  @Test
  void shouldFindAllByName() {

    // given
    /* TestData
     * id: 1
     * name: Logistics
     * description: Description text1
     * professor_id: 5 */
    String name = "logistics";

    // when
    List<Course> courses = underTest.findAllByName(name);
    // after returning the list,the comparison in name's value is being done in lowercase
    List<String> names = courses.stream()
        .flatMap(c -> Stream.of(c.getName()))
        .map(String::toLowerCase)
        .toList();

    // then
    assertThat(name.toLowerCase()).isIn(names);
  }

  @Test
  void shouldNotFindAllByName() {

    // given
    String name = "aNameThatDoesNotExist";

    // when
    List<Course> courses = underTest.findAllByName(name);
    List<String> names = courses.stream()
        .flatMap(c -> Stream.of(c.getName()))
        .toList();

    // then
    assertThat(names.isEmpty()).isTrue();
  }

  @Test
  void shouldExistByNameAndIdNot() {

    // given
    /* TestData
     * id: 1
     * name: Logistics
     * description: Description text1
     * professor_id: 5 */
    String name = "logistics";
    Long id = 10L;

    // when
    boolean expected = underTest.existsByNameAndIdIsNot(name, id);

    // then
    assertThat(expected).isTrue();
  }

  @Test
  void shouldNotExistByNameAndIdNot() {

    // given
    /* TestData
     * id: 1
     * name: Logistics
     * description: Description text1
     * professor_id: 5 */
    String name = "logistics";
    Long id = 1L;

    // when
    boolean expected = underTest.existsByNameAndIdIsNot(name, id);

    // then
    assertThat(expected).isFalse();
  }

  @Test
  void shouldDeleteAllByIdIn() {

    // given
    // TestData has 5 courses
    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    // when
    underTest.deleteAllByIdIn(ids);
    List<Course> allCourses = underTest.findAll();

    // then
    assertThat(allCourses.size()).isEqualTo(3);
  }

  @Test
  void shouldNotDeleteAllByIdIn() {

    // given
    // TestData has 5 courses
    Set<Long> ids = new HashSet<>();
    ids.add(10L);
    ids.add(11L);

    // when
    underTest.deleteAllByIdIn(ids);
    List<Course> allCourses = underTest.findAll();

    // then
    assertThat(allCourses.size()).isEqualTo(5);
  }
}